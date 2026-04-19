package com.hyunbindev.article.domain.vector.articlegraph

import com.hyunbindev.article.data.articlegraph.ArticleGraphDto
import kotlin.collections.set

class KruskalArticleGraphService(): ArticleGraph {
    override fun getArticleGraph(edges: List<VectorEdge>): ArticleGraphDto {
        val clusters = mutableMapOf<Long, List<Long>>()

        val parent = mutableMapOf<Long, Long>()

        val edges:List<VectorEdge> = edges.filter {
            val v = find(it.v,parent)
            val u = find(it.u,parent)
            if(v != u){
                parent[u] = v
                true
            }else{
                false
            }
        }.toList()

        for (nodeId in parent.keys) {
            val rootId = find(nodeId, parent)
            val list = clusters.getOrDefault(rootId, mutableListOf())
            clusters[rootId] = list + nodeId
        }

        return ArticleGraphDto(clusters,edges)
    }

    private fun find(n: Long, parent: MutableMap<Long, Long>): Long {
        val currentParent = parent[n] ?: n

        if (currentParent == n) {
            parent[n] = n // 명시적으로 저장
            return n
        }

        val root = find(currentParent, parent)
        parent[n] = root

        return root
    }

    private fun union(u:Long,v:Long, parent: MutableMap<Long, Long>) {
        val vRoot = find(v, parent)
        val uRoot = find(u, parent)
        if(uRoot != vRoot) parent[uRoot] = vRoot
    }
}