package com.hyunbindev.article.article.port.event.inbound

import com.hyunbindev.article.article.data.ArticleCreateEvent

interface ArticleCreatedEventHandler {
    fun handle(event: ArticleCreateEvent)
}