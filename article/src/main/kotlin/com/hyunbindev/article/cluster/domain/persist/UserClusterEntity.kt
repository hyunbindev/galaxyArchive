package com.hyunbindev.article.cluster.domain.persist

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class UserClusterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot_id", nullable = false)
    val snapshot: UserClusterSnapshotEntity,

    @Column(nullable = false)
    val label: Int,

    @Column(nullable = false)
    val articleCount: Int,

    @Column(nullable = false)
    val isNoise: Boolean = false,
)
