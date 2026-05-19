package com.hyunbindev.article.data.articlegraph

import kotlin.math.sqrt

data class ArticleGraphDto(
    val clusters: List<Cluster>,
    val nodes: List<Node>,
    val edges: List<Edge>
)

data class Point3D(
    val x:Double,
    val y:Double,
    val z:Double
){
    companion object {
        val ZERO get() = Point3D(0.0,0.0,0.0)
    }

    fun normalize(): Point3D {
        val len = sqrt(x * x + y * y + z * z)
        return if(len == 0.0) ZERO else Point3D(x/len,y/len,z/len)
    }

    fun dot(other: Point3D): Double = x*other.x + y*other.y + z*other.z


    operator fun times(scalar: Double): Point3D {
        return Point3D(x*scalar,y*scalar,z*scalar)
    }
};

data class Node(
    val id: Long,
    val title: String,
    val position:Point3D? = null
)

data class Cluster(
    val name:String,
    val position:Point3D,
    val nodeIds:List<Long>
)

data class Edge(
    val u:Node,
    val v:Node,
    val w:Float,
)