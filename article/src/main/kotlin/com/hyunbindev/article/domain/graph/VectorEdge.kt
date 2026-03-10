package com.hyunbindev.article.domain.graph

data class VectorEdge(
    val u:Long,
    val v:Long,
    val w:Float
) : Comparable<VectorEdge> {
    override fun compareTo(other: VectorEdge): Int {
        return other.w.compareTo(this.w)
    }
}