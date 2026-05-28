package com.hyunbindev.article.article.port.out

import com.hyunbindev.article.article.data.ArticleCreateEvent

interface ArticleEventPublisher {
    fun publishCreateEvent(event: ArticleCreateEvent)
}