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
    val nodeMap = nodes.associateBy { it.id }

    // 성단 크기(노드 개수)별 내림차순 정렬
    val sortedClusters = clusters.sortedByDescending { it.nodeIds.size }

    // 1. 성단 이름별 반지름 매핑
    val clusterRadius = sortedClusters.associate { cluster ->
        cluster.name to (sqrt(cluster.nodeIds.size.toDouble()) * CLUSTER_RADIUS_CONST + CLUSTER_MARGIN)
    }

    val clusterCenters = mutableMapOf<String, Point3D>()
    val nodePositionMap = mutableMapOf<Long, Point3D>()
    val totalClusterSize = sortedClusters.size

    // 2. 제2 코사인 법칙 기반 성단 중심점 배치 (기존의 훌륭한 충돌회피 로직 유지)
    for (i in sortedClusters.indices) {
        val cluster = sortedClusters[i]
        val radius = clusterRadius[cluster.name] ?: 10.0

        val y = if (totalClusterSize > 1) 1.0 - (i.toDouble() / (totalClusterSize - 1)) * 2.0 else 0.0
        val radiusAtY = sqrt(kotlin.math.max(0.0, 1.0 - y * y))
        val theta = GOLDEN_RATIO * i // 기존 황금비 상수 활용

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

            val distanceToPrevCenter = sqrt(prevCenter.x * prevCenter.x + prevCenter.y * prevCenter.y + prevCenter.z * prevCenter.z)
            val cosTheta = normalizedDir.dot(prevCenter.normalize())

            val b = -2.0 * distanceToPrevCenter * cosTheta
            val c = (distanceToPrevCenter * distanceToPrevCenter) - (minimumDistance * minimumDistance)
            val discriminant = b * b - 4.0 * c

            if (discriminant >= 0.0) {
                val sol = (-b + sqrt(discriminant)) / 2.0
                dist = kotlin.math.max(dist, sol)
            }
        }

        clusterCenters[cluster.name] = normalizedDir * dist

        // 3. 성단 내부 노드 초기 3D 구면 무작위 분사
        val currentCenter = clusterCenters[cluster.name] ?: Point3D.ZERO
        val maxR = sqrt(cluster.nodeIds.size.toDouble()) * 5.0

        cluster.nodeIds.forEach { nodeId ->
            val u = Math.random() * 2.0 * kotlin.math.PI
            val v = acos(2.0 * Math.random() - 1.0)
            val initR = maxR * Math.random().pow(1.0 / 3.0)

            nodePositionMap[nodeId] = Point3D(
                x = currentCenter.x + initR * sin(v) * cos(u),
                y = currentCenter.y + initR * sin(v) * sin(u),
                z = currentCenter.z + cos(v)
            )
        }

        // 4. Relaxation (인력/척력 안정화 시뮬레이션) 트리거
        val nodeSet = cluster.nodeIds.toSet()
        val clusterEdges = edges.filter { nodeSet.contains(it.u.id) && nodeSet.contains(it.v.id) }
        val iterations = 20

        for (step in 0 until iterations) {
            // [인력 연산 및 방향성 교정]
            for (edge in clusterEdges) {
                val pU = nodePositionMap[edge.u.id]
                val pV = nodePositionMap[edge.v.id]
                if (pU == null || pV == null) continue

                // pU에서 pV로 향하는 델타 벡터 계산
                val deltaX = pV.x - pU.x
                val deltaY = pV.y - pU.y
                val deltaZ = pV.z - pU.z
                val currentDist = sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ)
                if (currentDist == 0.0) continue

                // 코사인 거리(w) 기반 목표 거리 설정 (가까울수록 밀착)
                val targetDist = (edge.w + 0.1) * (maxR * 0.4)
                val force = (currentDist - targetDist) / currentDist

                // 탄성 텐션(0.3) 반영 및 방향 교정 벡터 생성
                // (서로 멀리 있으면 당기고, 목표보다 가까우면 밀어냄)
                val corrX = deltaX * force * 0.3
                val corrY = deltaY * force * 0.3
                val corrZ = deltaZ * force * 0.3

                nodePositionMap[edge.u.id] = Point3D(pU.x + corrX, pU.y + corrY, pU.z + corrZ)
                nodePositionMap[edge.v.id] = Point3D(pV.x - corrX, pV.y - corrY, pV.z - corrZ)
            }

            // [성단 외곽 탈출 방지 중력장 한계 계산]
            cluster.nodeIds.forEach { nodeId ->
                val pos = nodePositionMap[nodeId] ?: currentCenter
                val offsetX = pos.x - currentCenter.x
                val offsetY = pos.y - currentCenter.y
                val offsetZ = pos.z - currentCenter.z
                val dist = sqrt(offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ)

                if (dist > maxR) {
                    // 최대 반경을 벗어나면 멱살 잡고 구 표면에 강제 고정
                    val scale = maxR / dist
                    nodePositionMap[nodeId] = Point3D(
                        x = currentCenter.x + offsetX * scale,
                        y = currentCenter.y + offsetY * scale,
                        z = currentCenter.z + offsetZ * scale
                    )
                } else if (dist < 1.0) {
                    // 중심점 블랙홀 현상 방지
                    val scale = 1.0 / dist
                    nodePositionMap[nodeId] = Point3D(
                        x = currentCenter.x + offsetX * scale,
                        y = currentCenter.y + offsetY * scale,
                        z = currentCenter.z + offsetZ * scale
                    )
                }
            }
        }

        // 5. 최종 좌표 맵 변환 및 결과 빌드
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

    val allNodeIds = edges.flatMap { listOf(it.u, it.v) }.distinct()
    val trees:Map<Any,List<Long>> = allNodeIds.groupBy { id -> findParent(id) }

    return Mst(trees=trees,edges = resultEdge)
}