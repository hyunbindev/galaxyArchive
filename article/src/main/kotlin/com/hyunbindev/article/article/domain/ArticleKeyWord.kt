package com.hyunbindev.article.article.domain

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class ArticleKeyWordEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    val article: ArticleEntity,

    val keyword: String,

    val similarity: Float
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}