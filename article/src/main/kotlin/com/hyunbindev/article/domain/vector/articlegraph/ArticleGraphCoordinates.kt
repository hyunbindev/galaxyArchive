package com.hyunbindev.article.domain.vector.articlegraph

import com.hyunbindev.article.data.articlegraph.Cluster
import com.hyunbindev.article.data.articlegraph.Edge
import com.hyunbindev.article.data.articlegraph.Node
import com.hyunbindev.article.data.articlegraph.Point3D
import com.hyunbindev.article.domain.vector.repository.ArticleEdgeProjection


data class Mst(
    val trees: Map<Any,List<Long>>,
    val edges: List<Edge>
)


fun getNodeCoordinates(edges:List<Edge>, clusters:List<Cluster>):Map<String, List<Node>>{

}

fun getArticleGraph(edges:List<ArticleEdgeProjection>):ArticleGraph{
    val (trees, edges) = getMinimumSpanningTree(edges)

}

fun getClusters(trees:Map<Any,List<Long>>, edges:List<Edge>):List<Cluster>{
    val sortedTree = trees.entries.sortedByDescending { it.value.size }

    val clusterRadius: MutableMap<Int,Int> = mutableMapOf()
    val result:MutableList<Cluster> = mutableListOf()

    for(i in sortedTree.indices){
        clusterRadius[i] = sortedTree[i].value.size
        if(i == 0) {
            result.add(Cluster(name = i.toString(), position = Point3D.ZERO, sortedTree[i].value))
            continue
        }

    }
}

fun getMinimumSpanningTree(edges:List<ArticleEdgeProjection>):Mst{
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