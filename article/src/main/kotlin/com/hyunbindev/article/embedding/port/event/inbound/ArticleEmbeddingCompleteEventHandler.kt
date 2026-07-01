package com.hyunbindev.article.embedding.port.event.inbound

import com.hyunbindev.article.embedding.data.EmbeddingCompletedEvent

interface ArticleEmbeddingCompleteEventHandler {
    fun handle(message: EmbeddingCompletedEvent)
}