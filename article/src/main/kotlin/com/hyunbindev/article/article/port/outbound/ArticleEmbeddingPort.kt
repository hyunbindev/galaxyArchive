package com.hyunbindev.article.article.port.outbound

interface ArticleEmbeddingPort {
    fun embedArticle(articleRawText: String):FloatArray
}