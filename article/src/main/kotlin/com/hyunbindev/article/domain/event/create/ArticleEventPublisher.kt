package com.hyunbindev.article.domain.event.create

interface ArticleEventPublisher {
    fun publishCreateEvent(event:ArticleCreateEvent)
}