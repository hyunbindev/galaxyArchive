package com.hyunbindev.article.cluster.domain

import com.hyunbindev.article.cluster.data.ClusterKeywordDto

data class UserCluster(
    val clusterId: Long,
    val label: Int,
    val articleCount: Int,
    val isNoise: Boolean,
    val clusterArticles: List<ClusterArticle>,
    val keywords: List<ClusterKeywordDto>
)
