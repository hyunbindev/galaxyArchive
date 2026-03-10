package com.hyunbindev.article.application.service.vector.command

import com.hyunbindev.article.domain.entity.ArticleEntity
import com.hyunbindev.article.domain.entity.ArticleVectorEntity
import com.hyunbindev.article.domain.repository.ArticleRepository
import com.hyunbindev.article.domain.repository.ArticleVectorRepository
import jakarta.transaction.Transactional
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
 class ArticleVectorService(
    private val embeddingModel : EmbeddingModel,
    private val articleVectorRepository: ArticleVectorRepository,
    private val articleRepository: ArticleRepository
) {
    @Transactional
    fun createArticleVector(articleId:Long){
        val article: ArticleEntity = articleRepository.findByIdOrNull(articleId)?:throw RuntimeException("Article not found")
        val vector:FloatArray = embeddingModel.embed(article.text)

        val vectorArticle: ArticleVectorEntity = ArticleVectorEntity(
            article = article,
            vector = vector,
        )

        articleVectorRepository.save(vectorArticle)
    }
}