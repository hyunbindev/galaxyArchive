package com.hyunbindev.article.cluster.application.service.query

import com.hyunbindev.article.cluster.adapter.outbound.UserClusterSnapshotRepository
import com.hyunbindev.article.cluster.data.UserClusterStatusDto
import com.hyunbindev.article.cluster.domain.UserClusterSnapShot
import com.hyunbindev.article.cluster.domain.persist.ClusterSnapshotStatus
import com.hyunbindev.article.cluster.domain.persist.UserClusterSnapshotEntity
import com.hyunbindev.article.cluster.port.usecase.inbound.UserClusterStatusQueryUseCase
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class UserClusterStatusQueryService(
    private val userClusterSnapshotRepository: UserClusterSnapshotRepository
): UserClusterStatusQueryUseCase {
    override fun getUserClusterStatus(userId: UUID): UserClusterStatusDto {
        val recentSnapShot: UserClusterSnapshotEntity = userClusterSnapshotRepository.findFirstByUserIdAndStatusOrderByCreatedAtDesc(userId, ClusterSnapshotStatus.COMPLETED)
            ?:return UserClusterStatusDto.fallBack()
        return UserClusterStatusDto(
            articleCount = recentSnapShot.articleCount,
            clusterCount = recentSnapShot.clusterCount,
        )
    }
}