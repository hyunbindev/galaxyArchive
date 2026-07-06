package com.hyunbindev.article.cluster.application.service.query

import com.hyunbindev.article.cluster.adapter.outbound.UserClusterSnapshotRepository
import com.hyunbindev.article.cluster.domain.persist.ClusterSnapshotStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class UserClusterStatusQueryService(
    private val userClusterSnapshotRepository: UserClusterSnapshotRepository
) {
    fun getUserClusterStatus(userId: UUID) {
        val recentSnapShot = userClusterSnapshotRepository.findFirstByUserIdAndStatusOrderByCreatedAtDesc(userId, ClusterSnapshotStatus.COMPLETED)
    }
}