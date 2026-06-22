package com.hyunbindev.article.embedding.data

data class EmbeddingCompletedEvent(
    val articleId: Long,
    val denseVectors: FloatArray,
    val keywords:List<Keyword>
)

data class Keyword(
    val keyword:String,
    val score:Float
)