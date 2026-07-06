package com.hyunbindev.article.cluster.application.service.query

import com.hyunbindev.article.cluster.data.ClusterArticle
import com.hyunbindev.article.cluster.data.UserCluster
import com.hyunbindev.article.cluster.data.UserClusterSnapShot
import com.hyunbindev.article.cluster.domain.persist.ClusterArticleEntity
import com.hyunbindev.article.cluster.domain.persist.UserClusterEntity
import com.hyunbindev.article.cluster.domain.persist.UserClusterSnapshotEntity
import org.springframework.stereotype.Component

@Component
internal class ClusterGraphAssembler {
    fun assemble(
        snapshot: UserClusterSnapshotEntity,
        clusters: List<UserClusterEntity>,
        clusterArticles: List<ClusterArticleEntity>,
    ): UserClusterSnapShot {
        val articlesByClusterId = clusterArticles.groupBy { it.userCluster.id }

        return UserClusterSnapShot(
            snapshotId = snapshot.id!!,
            userId = snapshot.userId,
            createdAt = snapshot.createdAt,
            clusters = clusters
                .sortedWith(compareBy<UserClusterEntity> { it.isNoise }.thenBy { it.label })
                .map { cluster ->
                    UserCluster(
                        clusterId = cluster.id!!,
                        label = cluster.label,
                        articleCount = cluster.articleCount,
                        isNoise = cluster.isNoise,
                        clusterArticles = articlesByClusterId[cluster.id].orEmpty()
                            .sortedBy { it.article.id }
                            .map { clusterArticle ->
                                ClusterArticle(
                                    title = clusterArticle.article.title,
                                    articleId = clusterArticle.article.id!!,
                                    x = clusterArticle.x,
                                    y = clusterArticle.y,
                                    z = clusterArticle.z,
                                    probability = clusterArticle.probability,
                                    outlierScore = clusterArticle.outlierScore,
                                )
                            },
                    )
                },
            clustersCount = snapshot.clusterCount,
            articleCount = snapshot.articleCount,
        )
    }
}
