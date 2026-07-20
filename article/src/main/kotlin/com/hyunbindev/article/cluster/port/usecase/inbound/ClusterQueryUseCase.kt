package com.hyunbindev.article.cluster.port.usecase.inbound

import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.data.ArticleSummaryDto
import com.hyunbindev.article.cluster.domain.UserClusterSnapShot
import java.util.UUID

interface ClusterQueryUseCase {
    fun getUserRecentCompletedCluster(userId: UUID): UserClusterSnapShot
    fun getArticleInCluster(clusterId:Long): List<ArticleSummaryDto>
}
