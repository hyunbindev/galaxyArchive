package com.hyunbindev.article.article.port.outbound

import com.hyunbindev.article.article.data.ArticleCreateEvent

interface ArticleEventPublishPort {
    fun publishCreateEvent(event: ArticleCreateEvent)
}