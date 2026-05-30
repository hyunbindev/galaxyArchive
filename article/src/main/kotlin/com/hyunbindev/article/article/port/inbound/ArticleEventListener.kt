package com.hyunbindev.article.article.port.inbound

import com.hyunbindev.article.article.data.ArticleCreateEvent

interface ArticleEventListener {
    fun onArticleCreated(event: ArticleCreateEvent)
}