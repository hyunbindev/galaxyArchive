package com.hyunbindev.article.cluster.data

data class ClusterArticle(
    val title: String,
    val articleId: Long,
    val x: Float,
    val y: Float,
    val z: Float,
    val probability: Float?,
    val outlierScore: Float?,
)
