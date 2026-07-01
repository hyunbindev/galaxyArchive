package com.hyunbindev.article.article.port.event.outbound

import com.hyunbindev.article.article.data.ArticleCreateEvent

interface ArticleEventPublishPort {
    fun publishCreateEvent(event: ArticleCreateEvent)
}