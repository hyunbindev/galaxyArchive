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
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


private const val CLUSTER_RADIUS_CONST = 15
private const val GOLDEN_RATIO = 2.39996
private const val CLUSTER_MARGIN = 10

private data class Mst(
    val trees: Map<Any,List<Long>>,
    val edges: List<Edge>
)


fun getArticleGraph(edges: List<ArticleEdgeProjection>): ArticleGraphDto {
    val mst = getMinimumSpanningTree(edges)

    val clusters = getClusters(mst.trees)

    val allNodes = mst.edges.flatMap { listOf(it.u, it.v) }.distinct()
    val nodeCoordinatesMap = getNodeCoordinates(mst.edges, clusters, allNodes)

    return ArticleGraphDto(
        clusters = clusters,
        nodes = nodeCoordinatesMap.values.flatten(),
        edges = mst.edges
    )
}


private fun getClusterRadius(clusterSize:Int) = sqrt(clusterSize.toDouble()) * CLUSTER_RADIUS_CONST

private fun getNodeCoordinates(
    edges: List<Edge>,
    clusters: List<Cluster>,
    nodes: List<Node>
): Map<String, List<Node>> {
    val result = mutableMapOf<String, List<Node>>()
    val nodeMap = nodes.associateBy { it.id }

    val sortedClusters = clusters.sortedByDescending { it.nodeIds.size }
    val totalClusterSize = sortedClusters.size

    val clusterRadius = sortedClusters.associate { cluster ->
        cluster.name to (sqrt(cluster.nodeIds.size.toDouble()) * CLUSTER_RADIUS_CONST + CLUSTER_MARGIN)
    }

    val clusterCenters = mutableMapOf<String, Point3D>()
    val nodePositionMap = mutableMapOf<Long, Point3D>()

    // ── 성단 중심점 배치 (기존 제2 코사인 법칙) ──────────────────
    for (i in sortedClusters.indices) {
        val cluster = sortedClusters[i]
        val radius = clusterRadius[cluster.name] ?: 10.0

        val y = if (totalClusterSize > 1) 1.0 - (i.toDouble() / (totalClusterSize - 1)) * 2.0 else 0.0
        val radiusAtY = sqrt(kotlin.math.max(0.0, 1.0 - y * y))
        val theta = kotlin.math.PI * (3.0 - sqrt(5.0)) * i

        val normalizedDir = Point3D(
            x = cos(theta) * radiusAtY,
            y = y,
            z = sin(theta) * radiusAtY
        ).normalize()

        var dist = 0.0
        for (j in 0 until i) {
            val prevCluster = sortedClusters[j]
            val prevCenter = clusterCenters[prevCluster.name] ?: Point3D.ZERO
            val prevRadius = clusterRadius[prevCluster.name] ?: 10.0
            val minimumDistance = radius + prevRadius
            val distanceToPrevCenter = sqrt(
                prevCenter.x.pow(2) + prevCenter.y.pow(2) + prevCenter.z.pow(2)
            )
            val cosTheta = normalizedDir.dot(prevCenter.normalize())
            val b = -2.0 * distanceToPrevCenter * cosTheta
            val c = distanceToPrevCenter.pow(2) - minimumDistance.pow(2)
            val discriminant = b * b - 4.0 * c
            if (discriminant >= 0.0) {
                dist = kotlin.math.max(dist, (-b + sqrt(discriminant)) / 2.0)
            }
        }
        clusterCenters[cluster.name] = normalizedDir * dist
    }

    // ── 성단별 Force-directed 배치 ────────────────────────────────
    for (cluster in sortedClusters) {
        val currentCenter = clusterCenters[cluster.name] ?: Point3D.ZERO
        val maxR = sqrt(cluster.nodeIds.size.toDouble()) * CLUSTER_RADIUS_CONST
        val nodeSet = cluster.nodeIds.toSet()

        // 인접 리스트 구성
        val adjacency = mutableMapOf<Long, MutableList<Pair<Long, Double>>>()
        cluster.nodeIds.forEach { adjacency[it] = mutableListOf() }
        val clusterEdges = edges.filter { nodeSet.contains(it.u.id) && nodeSet.contains(it.v.id) }
        clusterEdges.forEach { edge ->
            adjacency[edge.u.id]?.add(Pair(edge.v.id, edge.w.toDouble()))
            adjacency[edge.v.id]?.add(Pair(edge.u.id, edge.w.toDouble()))
        }

        // ── BFS depth 기반 초기 배치 (Force-directed 시작점) ─────
        val root = cluster.nodeIds.first()
        val visited = mutableSetOf(root)
        val depthMap = mutableMapOf(root to 0)
        val queue: java.util.LinkedList<Long> = java.util.LinkedList()
        queue.add(root)
        val maxDepth: Int

        while (queue.isNotEmpty()) {
            val cur = queue.poll()
            adjacency[cur]?.forEach { (neighbor, _) ->
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor)
                    depthMap[neighbor] = (depthMap[cur] ?: 0) + 1
                    queue.add(neighbor)
                }
            }
        }
        maxDepth = depthMap.values.maxOrNull()?.takeIf { it > 0 } ?: 1

        // 같은 depth 노드끼리 구면상에 균등 배치
        val depthGroups = cluster.nodeIds.groupBy { depthMap[it] ?: 0 }
        depthGroups.forEach { (d, nodesAtDepth) ->
            val r = (d.toDouble() / maxDepth) * maxR * 0.8 + 1.0
            nodesAtDepth.forEachIndexed { idx, nodeId ->
                val goldenAngle = kotlin.math.PI * (3.0 - sqrt(5.0))
                val yy = if (nodesAtDepth.size > 1) 1.0 - (idx.toDouble() / (nodesAtDepth.size - 1)) * 2.0 else 0.0
                val radiusAtYY = sqrt(kotlin.math.max(0.0, 1.0 - yy * yy))
                val phi = goldenAngle * idx
                nodePositionMap[nodeId] = Point3D(
                    x = currentCenter.x + r * radiusAtYY * cos(phi),
                    y = currentCenter.y + r * yy,
                    z = currentCenter.z + r * radiusAtYY * sin(phi)
                )
            }
        }

        // 고립 노드 처리
        cluster.nodeIds.filter { !nodePositionMap.containsKey(it) }.forEachIndexed { idx, nodeId ->
            val phi = idx * 2.0 * kotlin.math.PI / cluster.nodeIds.size
            nodePositionMap[nodeId] = Point3D(
                x = currentCenter.x + maxR * 0.3 * cos(phi),
                y = currentCenter.y + maxR * 0.3 * sin(phi),
                z = currentCenter.z
            )
        }

        // ── Force-directed 시뮬레이션 ─────────────────────────────
        val SPRING_LENGTH   = maxR / maxDepth.toDouble()  // 엣지 자연 길이
        val SPRING_K        = 0.15   // 스프링 강도
        val REPULSION_K     = SPRING_LENGTH.pow(2) * 2.0  // 척력 상수
        val CENTER_GRAVITY  = 0.01   // 중심 인력
        val DAMPING         = 0.85   // 속도 감쇠
        val iterations      = 120

        // 속도 벡터
        val velocity = mutableMapOf<Long, Point3D>()
        cluster.nodeIds.forEach { velocity[it] = Point3D(0.0, 0.0, 0.0) }

        for (step in 0 until iterations) {
            val force = mutableMapOf<Long, Point3D>()
            cluster.nodeIds.forEach { force[it] = Point3D(0.0, 0.0, 0.0) }

            // 1. 척력 (모든 노드쌍)
            val ids = cluster.nodeIds
            for (a in ids.indices) {
                for (b in a + 1 until ids.size) {
                    val idA = ids[a]; val idB = ids[b]
                    val pA = nodePositionMap[idA] ?: continue
                    val pB = nodePositionMap[idB] ?: continue

                    val dx = pA.x - pB.x
                    val dy = pA.y - pB.y
                    val dz = pA.z - pB.z
                    val distSq = (dx * dx + dy * dy + dz * dz).coerceAtLeast(0.01)
                    val dist = sqrt(distSq)
                    val repulse = REPULSION_K / distSq

                    val fx = repulse * dx / dist
                    val fy = repulse * dy / dist
                    val fz = repulse * dz / dist

                    force[idA] = force[idA]!!.let { Point3D(it.x + fx, it.y + fy, it.z + fz) }
                    force[idB] = force[idB]!!.let { Point3D(it.x - fx, it.y - fy, it.z - fz) }
                }
            }

            // 2. 스프링 인력 (연결된 엣지)
            for (edge in clusterEdges) {
                val pU = nodePositionMap[edge.u.id] ?: continue
                val pV = nodePositionMap[edge.v.id] ?: continue

                val dx = pV.x - pU.x
                val dy = pV.y - pU.y
                val dz = pV.z - pU.z
                val dist = sqrt(dx * dx + dy * dy + dz * dz).coerceAtLeast(0.01)

                // w가 작을수록(유사도 높을수록) 자연 길이를 짧게
                val naturalLen = SPRING_LENGTH * (edge.w + 0.1)
                val springF = SPRING_K * (dist - naturalLen) / dist

                val fx = springF * dx; val fy = springF * dy; val fz = springF * dz

                force[edge.u.id] = force[edge.u.id]!!.let { Point3D(it.x + fx, it.y + fy, it.z + fz) }
                force[edge.v.id] = force[edge.v.id]!!.let { Point3D(it.x - fx, it.y - fy, it.z - fz) }
            }

            // 3. 중심 인력 (drift 방지)
            cluster.nodeIds.forEach { nodeId ->
                val pos = nodePositionMap[nodeId] ?: return@forEach
                val dx = currentCenter.x - pos.x
                val dy = currentCenter.y - pos.y
                val dz = currentCenter.z - pos.z
                force[nodeId] = force[nodeId]!!.let {
                    Point3D(
                        it.x + CENTER_GRAVITY * dx,
                        it.y + CENTER_GRAVITY * dy,
                        it.z + CENTER_GRAVITY * dz
                    )
                }
            }

            // 4. 속도 + 위치 업데이트
            cluster.nodeIds.forEach { nodeId ->
                val v = velocity[nodeId] ?: Point3D.ZERO
                val f = force[nodeId] ?: Point3D.ZERO

                val newVx = (v.x + f.x) * DAMPING
                val newVy = (v.y + f.y) * DAMPING
                val newVz = (v.z + f.z) * DAMPING
                velocity[nodeId] = Point3D(newVx, newVy, newVz)

                val pos = nodePositionMap[nodeId] ?: currentCenter
                nodePositionMap[nodeId] = Point3D(pos.x + newVx, pos.y + newVy, pos.z + newVz)
            }

            // 5. 성단 경계 초과 방지
            cluster.nodeIds.forEach { nodeId ->
                val pos = nodePositionMap[nodeId] ?: currentCenter
                val ox = pos.x - currentCenter.x
                val oy = pos.y - currentCenter.y
                val oz = pos.z - currentCenter.z
                val d = sqrt(ox * ox + oy * oy + oz * oz)
                if (d > maxR && d > 0.0) {
                    val scale = maxR / d
                    nodePositionMap[nodeId] = Point3D(
                        currentCenter.x + ox * scale,
                        currentCenter.y + oy * scale,
                        currentCenter.z + oz * scale
                    )
                    // 경계 충돌시 속도 감쇠
                    velocity[nodeId] = velocity[nodeId]!!.let {
                        Point3D(it.x * 0.3, it.y * 0.3, it.z * 0.3)
                    }
                }
            }
        }

        val clusterNodes = cluster.nodeIds.map { id ->
            Node(
                id = id,
                title = nodeMap[id]?.title ?: "",
                position = nodePositionMap[id] ?: currentCenter
            )
        }
        result[cluster.name] = clusterNodes
    }

    return result
}

private fun getClusters(trees:Map<Any,List<Long>>):List<Cluster>{
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

    val sortedEdges = edges.sortedBy { it.w }

    for(edge in sortedEdges){
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

    for(edge in sortedEdges){
        if(union(edge.u,edge.v)){
            val uNode = Node(edge.u,edge.u_title)
            val vNode = Node(edge.v,edge.v_title)
            resultEdge.add(Edge(uNode,vNode,edge.w*10+1))
        }
    }

    val allNodeIds = resultEdge.flatMap { listOf(it.u.id, it.v.id) }.distinct()
    val trees:Map<Any,List<Long>> = allNodeIds.groupBy { id -> findParent(id) }

    return Mst(trees=trees,edges = resultEdge)
}