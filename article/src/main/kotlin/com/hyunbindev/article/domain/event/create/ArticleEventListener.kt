package com.hyunbindev.article.domain.event.create

interface ArticleEventListener {
    fun onArticleCreated(event: ArticleCreateEvent)
}