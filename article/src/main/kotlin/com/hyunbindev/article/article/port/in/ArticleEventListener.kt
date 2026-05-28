package com.hyunbindev.article.article.port.`in`

import com.hyunbindev.article.article.data.ArticleCreateEvent

interface ArticleEventListener {
    fun onArticleCreated(event: ArticleCreateEvent)
}