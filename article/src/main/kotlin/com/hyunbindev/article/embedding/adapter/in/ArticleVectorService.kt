package com.hyunbindev.article.embedding.adapter.`in`

import com.hyunbindev.article.article.domain.ArticleStatus
import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.embedding.domain.ArticleVectorEntity
import com.hyunbindev.article.article.adapter.outbound.ArticleRepository
import com.hyunbindev.article.article.port.outbound.ArticleEmbeddingPort
import com.hyunbindev.article.embedding.adapter.out.ArticleVectorRepository
import com.hyunbindev.article.embedding.port.`in`.CreateArticleVectorUseCase
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
internal class ArticleVectorService(
    private val articleEmbeddingPort : ArticleEmbeddingPort,
    private val articleVectorRepository: ArticleVectorRepository,
    private val articleRepository: ArticleRepository
): CreateArticleVectorUseCase {
    @Transactional
    override fun createArticleVector(articleId:Long){
        val article: ArticleEntity = articleRepository.findByIdOrNull(articleId)?:throw RuntimeException("Article not found")
        val vector:FloatArray = articleEmbeddingPort.embedArticle(article.rawText)

        article.status=ArticleStatus.COMPLETED

        val vectorArticle: ArticleVectorEntity = ArticleVectorEntity(
            article = article,
            vector = vector,
        )

        articleVectorRepository.save(vectorArticle)
    }
}