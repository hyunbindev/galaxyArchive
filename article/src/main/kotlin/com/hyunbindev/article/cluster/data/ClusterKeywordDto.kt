package com.hyunbindev.article.cluster.data

data class ClusterKeywordDto(
    val clusterId:Long,
    val keyword:String,
    val similarity:Double
)