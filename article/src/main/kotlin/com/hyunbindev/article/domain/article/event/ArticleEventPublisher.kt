package com.hyunbindev.article.domain.article.event

interface ArticleEventPublisher {
    fun publishCreateEvent(event:ArticleCreateEvent)
}