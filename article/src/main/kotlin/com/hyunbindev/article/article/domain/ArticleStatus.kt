package com.hyunbindev.article.article.domain

enum class ArticleStatus(val status:String) {
    DRAFT("DRAFT"),
    PENDING("PENDING"),
    CALCULATING("CALCULATING"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED")
}