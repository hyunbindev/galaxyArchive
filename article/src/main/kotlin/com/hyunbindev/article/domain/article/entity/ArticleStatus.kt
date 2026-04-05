package com.hyunbindev.article.domain.article.entity

enum class ArticleStatus(val status:String) {
    DRAFT("DRAFT"),
    PENDING("PENDING"),
    CALCULATING("CALCULATING"),
    COMPLETED("COMPLETED"),
}