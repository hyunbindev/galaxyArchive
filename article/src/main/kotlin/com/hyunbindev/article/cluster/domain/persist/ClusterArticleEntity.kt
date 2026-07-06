package com.hyunbindev.article.cluster.domain.persist

import com.hyunbindev.article.article.domain.ArticleEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ClusterArticleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    val article: ArticleEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id", nullable = false)
    val userCluster: UserClusterEntity,

    @Column(nullable = true)
    val probability: Float? = null,

    @Column(nullable = true)
    val outlierScore: Float? = null,

    @Column(nullable = false)
    val x: Float,

    @Column(nullable = false)
    val y: Float,

    @Column(nullable = false)
    val z: Float,
)