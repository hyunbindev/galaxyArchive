package com.hyunbindev.article.domain.article.event

interface ArticleEventListener {
    fun onArticleCreated(event: ArticleCreateEvent)
}