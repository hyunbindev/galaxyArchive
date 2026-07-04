package com.hyunbindev.article.cluster.adapter.outbound

import com.hyunbindev.article.cluster.domain.UserClusterEntity
import com.hyunbindev.article.cluster.domain.UserClusterSnapshotEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserClusterRepository : JpaRepository<UserClusterEntity, Long> {
    fun findAllBySnapshot(snapshot: UserClusterSnapshotEntity): List<UserClusterEntity>
}
