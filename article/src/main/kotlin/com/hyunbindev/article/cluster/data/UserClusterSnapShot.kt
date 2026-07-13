package com.hyunbindev.article.cluster.data

import java.time.LocalDateTime
import java.util.UUID

data class UserClusterSnapShot(
    val snapshotId: Long,
    val userId: UUID,
    val createdAt: LocalDateTime,
    val clusters: List<UserCluster>,
    val clustersCount: Int,
    val articleCount: Int,
)
