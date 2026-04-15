package com.hyunbindev.article.domain.vector

data class VectorEdge(
    val u_title:String,
    val v_title:String,
    val u:Long,
    val v:Long,
    val w:Float
) : Comparable<VectorEdge> {
    override fun compareTo(other: VectorEdge): Int {
        return this.w.compareTo(other.w)
    }
}