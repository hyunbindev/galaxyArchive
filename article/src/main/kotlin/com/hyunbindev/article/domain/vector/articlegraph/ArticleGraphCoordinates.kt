package com.hyunbindev.article.domain.vector.articlegraph

import com.hyunbindev.article.data.articlegraph.ArticleGraphDto
import com.hyunbindev.article.data.articlegraph.Cluster
import com.hyunbindev.article.data.articlegraph.Edge
import com.hyunbindev.article.data.articlegraph.Node
import com.hyunbindev.article.data.articlegraph.Point3D
import com.hyunbindev.article.domain.vector.repository.ArticleEdgeProjection
import java.util.LinkedList
import java.util.Queue
import kotlin.collections.plus
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


fun getArticleGraph(edges: List<ArticleEdgeProjection>): ArticleGraphDto {
    val mst = getMinimumSpanningTree(edges)

    val clusters = getClusters(mst.trees, mst.edges)

    val allNodes = mst.edges.flatMap { listOf(it.u, it.v) }.distinct()
    val nodeCoordinatesMap = getNodeCoordinates(mst.edges, clusters, allNodes)

    return ArticleGraphDto(
        clusters = clusters,
        nodes = nodeCoordinatesMap.values.flatten(),
        edges = mst.edges
    )
}

private const val CLUSTER_RADIUS_CONST = 5
private const val GOLDEN_RATIO = 2.39996
private const val CLUSTER_MARGIN = 10

private data class Mst(
    val trees: Map<Any,List<Long>>,
    val edges: List<Edge>
)

private fun getClusterRadius(clusterSize:Int) = sqrt(clusterSize.toDouble()) * CLUSTER_RADIUS_CONST


private fun getNodeCoordinates(edges: List<Edge>, clusters: List<Cluster>, nodes: List<Node>): Map<String, List<Node>> {
    val result = mutableMapOf<String, List<Node>>()

    val adj: MutableMap<Long, MutableList<Edge>> = mutableMapOf()

    val nodeMap = nodes.associateBy { it.id }.toMutableMap()

    edges.forEach { edge ->
        adj.getOrPut(edge.u.id) { mutableListOf() }.add(edge)
        adj.getOrPut(edge.v.id) { mutableListOf() }.add(edge)

        if (!nodeMap.containsKey(edge.u.id)) nodeMap[edge.u.id] = edge.u
        if (!nodeMap.containsKey(edge.v.id)) nodeMap[edge.v.id] = edge.v
    }

    clusters.forEach { cluster ->
        val nodeIds: Set<Long> = cluster.nodeIds.toSet()

        if (nodeIds.isEmpty()) return@forEach

        val positionedNodeMap = mutableMapOf<Long, Node>()
        val visited = mutableSetOf<Long>()

        val rootId = nodeIds.first()
        // cluster.position은 확실히 Point3D이므로 안전함
        positionedNodeMap[rootId] = Node(id = rootId, title = nodeMap[rootId]?.title ?: "", position = cluster.position)
        visited.add(rootId)

        val queue: Queue<Long> = LinkedList<Long>()
        queue.add(rootId)

        var branchCounter = 0

        while (queue.isNotEmpty()) {
            val parentId = queue.poll()
            val parentNode = positionedNodeMap[parentId]!!

            val childrenEdges = adj[parentId]?.filter { edge ->
                val childId = if (edge.u.id == parentId) edge.v.id else edge.u.id
                nodeIds.contains(childId) && !visited.contains(childId)
            } ?: emptyList()

            childrenEdges.forEach { edge ->
                val childId = if (edge.u.id == parentId) edge.v.id else edge.u.id

                branchCounter++
                val phi = branchCounter * GOLDEN_RATIO
                val theta = acos(1.0 - 2.0 * (branchCounter % 100 + 0.5) / 100.0)
                val dir = Point3D(sin(theta) * cos(phi), sin(theta) * sin(phi), cos(theta)).normalize()

                val distance = kotlin.math.max(2.0, edge.w * 5.0)

                // 부모 좌표가 null일 경우를 대비해 cluster.position을 백업으로 작동하도록 안전 마킹
                val basePos = parentNode.position ?: cluster.position
                val childPosition = Point3D(
                    x = basePos.x + (dir.x * distance),
                    y = basePos.y + (dir.y * distance),
                    z = basePos.z + (dir.z * distance)
                )

                val childTitle = nodeMap[childId]?.title ?: ""
                positionedNodeMap[childId] = Node(id = childId, title = childTitle, position = childPosition)

                visited.add(childId)
                queue.add(childId)
            }
        }

        nodeIds.forEach { id ->
            if (!positionedNodeMap.containsKey(id)) {
                positionedNodeMap[id] = Node(id = id, title = nodeMap[id]?.title ?: "", position = cluster.position)
            }
        }

        result[cluster.name] = positionedNodeMap.values.toList()
    }
    return result
}

private fun getClusters(trees:Map<Any,List<Long>>, edges:List<Edge>):List<Cluster>{
    val sortedTree = trees.entries.sortedByDescending { it.value.size }

    val clusterRadius: MutableMap<Int,Int> = mutableMapOf()
    val result:MutableList<Cluster> = mutableListOf()

    val centerRadius: Double = getClusterRadius(sortedTree[0].value.size)

    for(i in sortedTree.indices){
        clusterRadius[i] = sortedTree[i].value.size

        //TODO - implementation cluster name
        val clusterName:String = i.toString()
        if(i == 0) {
            result.add(Cluster(name = clusterName, position = Point3D.ZERO, sortedTree[i].value))
            continue
        }
        val phi = i * GOLDEN_RATIO
        val theta = acos(1.0 - 2.0 * (i + 0.5) / sortedTree.size)
        val normalizedDir =
            Point3D(sin(theta) * cos(phi), sin(theta) * sin(phi), cos(theta)).normalize()


        val radius = getClusterRadius(clusterRadius[i]!!)

        var dist = centerRadius +
                  radius +
                  CLUSTER_MARGIN

        for (j in 0 until i) {
            val prevCluster = result[j] // 이미 배치되어 result에 들어간 앞선 클러스터 꺼내기
            val prevRealRadius = getClusterRadius(clusterRadius[j]!!)

            // 두 구체가 유지해야 할 최소 중심 거리
            val minDist = radius + prevRealRadius + CLUSTER_MARGIN

            // prevCenter의 원점으로부터의 직선 거리
            val prevCenter = prevCluster.position
            val len = sqrt(prevCenter.x * prevCenter.x + prevCenter.y * prevCenter.y + prevCenter.z * prevCenter.z)

            // 두 벡터의 내적을 통한 cos 계산
            val dotProduct = normalizedDir.dot(prevCenter)
            val cosValue = dotProduct / (if (len == 0.0) 1.0 else len)

            // 2차 방정식 계수 결정 (a는 normalizedDir이 단위벡터이므로 1)
            val b = -2.0 * len * cosValue
            val c = (len * len) - (minDist * minDist)
            val d = (b * b) - (4.0 * c) // 판별식

            // 충돌 가능성이 있다면 제2코사인 법칙에 따라 전진 거리 계산
            if (d >= 0.0) {
                val sol = (-b + sqrt(d)) / 2.0
                dist = kotlin.math.max(dist, sol) // 기존 거리보다 멀리 가야 하면 갱신
            }
        }
        val position = normalizedDir * dist
        result.add(Cluster(name = clusterName, position = position, nodeIds = sortedTree[i].value))
    }
    return result
}

private fun getMinimumSpanningTree(edges:List<ArticleEdgeProjection>):Mst{
    val parent:MutableMap<Long,Long> = mutableMapOf()

    for(edge in edges){
        parent[edge.u]=edge.u
        parent[edge.v]=edge.v
    }

    fun findParent(node:Long):Long{
        if(parent[node]==node) return node

        parent[node] = if(parent[node]==null) node else findParent(parent[node]!!)

        return parent[node]!!
    }

    fun union(node1:Long,node2:Long):Boolean{
        val root1 = findParent(node1)
        val root2 = findParent(node2)

        if(root1==root2) return false

        parent[root2] = root1

        return true
    }


    val resultEdge:MutableList<Edge> = mutableListOf()

    for(edge in edges){
        if(union(edge.u,edge.v)){
            val uNode = Node(edge.u,edge.u_title)
            val vNode = Node(edge.v,edge.v_title)
            resultEdge.add(Edge(uNode,vNode,edge.w))
        }
    }

    val allNodeIds = edges.flatMap { listOf(it.u, it.v) }.distinct()
    val trees:Map<Any,List<Long>> = allNodeIds.groupBy { id -> findParent(id) }

    return Mst(trees=trees,edges = resultEdge)
}