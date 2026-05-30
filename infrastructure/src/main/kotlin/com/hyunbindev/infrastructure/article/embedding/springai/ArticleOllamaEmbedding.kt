package com.hyunbindev.infrastructure.article.embedding.springai

import com.hyunbindev.article.article.port.outbound.ArticleEmbeddingPort
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.stereotype.Component

@Component
class ArticleOllamaEmbedding(
    private val embeddingModel : EmbeddingModel
): ArticleEmbeddingPort {
    override fun embedArticle(articleRawText: String):FloatArray {
        return embeddingModel.embed(articleRawText)
    }
}