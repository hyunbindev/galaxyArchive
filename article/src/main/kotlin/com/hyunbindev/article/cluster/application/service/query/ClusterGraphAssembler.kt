package com.hyunbindev.article.cluster.application.service.query

import com.hyunbindev.article.article.data.ArticleKeywordDto
import com.hyunbindev.article.cluster.data.ClusterArticle
import com.hyunbindev.article.cluster.data.ClusterKeywordDto
import com.hyunbindev.article.cluster.data.UserCluster
import com.hyunbindev.article.cluster.data.UserClusterSnapShot
import com.hyunbindev.article.cluster.domain.persist.ClusterArticleEntity
import com.hyunbindev.article.cluster.domain.persist.UserClusterEntity
import com.hyunbindev.article.cluster.domain.persist.UserClusterSnapshotEntity
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ArticleExceptionCode
import org.springframework.stereotype.Component

@Component
internal class ClusterGraphAssembler {
    fun assemble(
        snapshot: UserClusterSnapshotEntity,
        clusters: List<UserClusterEntity>,
        clusterArticles: List<ClusterArticleEntity>,
        clusterKeyWords: List<ClusterKeywordDto>
    ): UserClusterSnapShot {
        val snapshotId = snapshot.id?: throw ArticleException(ArticleExceptionCode.ARTICLE_INTERNAL_ERROR)

        val userClusters = mapUserCluster(clusters, clusterArticles, clusterKeyWords)

        return UserClusterSnapShot(
            snapshotId = snapshotId,
            userId = snapshot.userId,
            createdAt = snapshot.createdAt,
            clusters = userClusters,
            clustersCount = clusters.size,
            articleCount = snapshot.articleCount,
        )
    }

    private fun mapUserCluster(
        clusterEntities: List<UserClusterEntity>,
        clusterArticles: List<ClusterArticleEntity>,
        keywords: List<ClusterKeywordDto>,
    ): List<UserCluster> {
        val articlesByClusterId = clusterArticles.groupBy {
            it.userCluster.id ?: throw ArticleException(ArticleExceptionCode.ARTICLE_INTERNAL_ERROR)
        }

        return clusterEntities
            .sortedWith(compareBy<UserClusterEntity> { it.isNoise }.thenBy { it.label })
            .map { cluster ->
                val clusterId = cluster.id
                    ?: throw ArticleException(ArticleExceptionCode.ARTICLE_INTERNAL_ERROR)

                val articlesInCluster = articlesByClusterId[clusterId].orEmpty()

                val keywordMap:Map<Long,List<ClusterKeywordDto>> = keywords.groupBy { it.clusterId }

                UserCluster(
                    clusterId = clusterId,
                    label = cluster.label,
                    articleCount = cluster.articleCount,
                    isNoise = cluster.isNoise,
                    keywords = keywordMap[clusterId].orEmpty(),
                    clusterArticles = mapArticleInCluster(articlesInCluster),
                )
            }
    }

    private fun mapArticleInCluster(clusterArticles: List<ClusterArticleEntity>): List<ClusterArticle> {
        return clusterArticles
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
            }
    }
}
