package com.hyunbindev.article.data.articlegraph

data class ArticleGraphDto(
    val clusters: List<Cluster>
)

data class Point3D(
    val x:Double,
    val y:Double,
    val z:Double
){
    companion object {
        val ZERO get() =Point3D(0.0,0.0,0.0)
    }
};

data class Node(
    val id: Long,
    val title: String,
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