package com.hyunbindev.article.application.service.vector.command

import com.hyunbindev.article.domain.article.entity.ArticleStatus
import com.hyunbindev.article.domain.article.entity.ArticleEntity
import com.hyunbindev.article.domain.vector.entity.ArticleVectorEntity
import com.hyunbindev.article.domain.article.repository.ArticleRepository
import com.hyunbindev.article.domain.vector.repository.ArticleVectorRepository
import jakarta.transaction.Transactional
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


//TODO- 캡슐화로 internal class 로 전환 필요 interface 작성으로 의존성 역전 필요
@Service
 class ArticleVectorService(
    private val embeddingModel : EmbeddingModel,
    private val articleVectorRepository: ArticleVectorRepository,
    private val articleRepository: ArticleRepository
) {
    @Transactional
    fun createArticleVector(articleId:Long){
        val article: ArticleEntity = articleRepository.findByIdOrNull(articleId)?:throw RuntimeException("Article not found")
        val vector:FloatArray = embeddingModel.embed(article.rawText)
        article.status=ArticleStatus.COMPLETED

        val vectorArticle: ArticleVectorEntity = ArticleVectorEntity(
            article = article,
            vector = vector,
        )

        articleVectorRepository.save(vectorArticle)
    }
}