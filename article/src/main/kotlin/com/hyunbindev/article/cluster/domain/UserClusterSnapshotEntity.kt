package com.hyunbindev.article.cluster.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.UUID

@Entity
class UserClusterSnapshotEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val userId: UUID,

    @Column(nullable = false, unique = true)
    val runId: UUID,

    @Column(nullable = false)
    val algorithm: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: ClusterSnapshotStatus = ClusterSnapshotStatus.COMPLETED,

    @Column(nullable = false)
    val articleCount: Int,

    @Column(nullable = false)
    val clusterCount: Int,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

enum class ClusterSnapshotStatus {
    COMPLETED,
    FAILED,
}
