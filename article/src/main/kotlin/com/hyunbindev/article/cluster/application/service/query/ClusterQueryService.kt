package com.hyunbindev.article.cluster.application.service.query

import com.hyunbindev.article.cluster.adapter.outbound.ClusterArticleRepository
import com.hyunbindev.article.cluster.adapter.outbound.UserClusterRepository
import com.hyunbindev.article.cluster.adapter.outbound.UserClusterSnapshotRepository
import com.hyunbindev.article.cluster.data.UserClusterSnapShot
import com.hyunbindev.article.cluster.domain.persist.ClusterSnapshotStatus
import com.hyunbindev.article.cluster.port.usecase.inbound.ClusterQueryUseCase
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ClusterExceptionCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
internal class ClusterQueryService(
    private val clusterArticleRepository: ClusterArticleRepository,
    private val userClusterRepository: UserClusterRepository,
    private val userClusterSnapshotRepository: UserClusterSnapshotRepository,
    private val clusterGraphAssembler: ClusterGraphAssembler,
) : ClusterQueryUseCase {
    override fun getUserRecentCompletedCluster(userId: UUID): UserClusterSnapShot {
        val snapshot = userClusterSnapshotRepository.findFirstByUserIdAndStatusOrderByCreatedAtDesc(
            userId,
            ClusterSnapshotStatus.COMPLETED
        ) ?: throw ArticleException(ClusterExceptionCode.NO_USER_CLUSTER)

        val clusters = userClusterRepository.findAllBySnapshot(snapshot)
        val clusterArticles = clusterArticleRepository.findAllByUserClusterInFetchArticle(clusters)

        return clusterGraphAssembler.assemble(
            snapshot = snapshot,
            clusters = clusters,
            clusterArticles = clusterArticles,
        )
    }
}
