package com.hyunbindev.article.domain.graph

class VectorMST(val edges:List<VectorEdge>) {
    fun getMST():List<VectorEdge> {
        val sortedEdges = edges.sorted()


        return sortedEdges
    }
    private fun unionFind()
}