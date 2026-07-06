package com.hyunbindev.article.cluster.adapter.outbound

import com.hyunbindev.article.cluster.domain.persist.ClusterSnapshotStatus
import com.hyunbindev.article.cluster.domain.persist.UserClusterSnapshotEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserClusterSnapshotRepository : JpaRepository<UserClusterSnapshotEntity, Long> {
    fun findFirstByUserIdAndStatusOrderByCreatedAtDesc(
        userId: UUID,
        status: ClusterSnapshotStatus,
    ): UserClusterSnapshotEntity?
}