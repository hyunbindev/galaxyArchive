package com.hyunbindev.article.embedding.data

data class EmbeddingCompletedEvent(
    val articleId: Long,
    val status: String,
    val error: String?=null,
)