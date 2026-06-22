package com.hyunbindev.article.embedding.port.inbound

import com.hyunbindev.article.embedding.data.EmbeddingCompletedEvent

interface EmbeddingArticleUseCase {
    fun embeddingArticle(message: EmbeddingCompletedEvent)
    fun failEmbeddingArticle(articleId:Long)
}