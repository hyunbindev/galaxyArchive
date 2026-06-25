package com.hyunbindev.article.embedding.application.service.command

import com.hyunbindev.article.article.adapter.outbound.ArticleRepository
import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.domain.ArticleStatus
import com.hyunbindev.article.article.adapter.outbound.ArticleKeywordRepository
import com.hyunbindev.article.embedding.adapter.outbound.ArticleVectorRepository
import com.hyunbindev.article.embedding.data.EmbeddingCompletedEvent
import com.hyunbindev.article.article.domain.ArticleKeyWordEntity
import com.hyunbindev.article.embedding.domain.ArticleVectorEntity
import com.hyunbindev.article.embedding.port.inbound.EmbeddingArticleUseCase
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
internal class EmbeddingArticleService(
    private val articleVectorRepository: ArticleVectorRepository,
    private val articleRepository: ArticleRepository,
    private val articleKeywordRepository: ArticleKeywordRepository
): EmbeddingArticleUseCase {

    @Transactional
    override fun embeddingArticle(message: EmbeddingCompletedEvent) {
        //게시글이 없을 경우 로직 종료
        val article:ArticleEntity = articleRepository.findArticleById(message.articleId)?:return;

        val articleVector: ArticleVectorEntity = ArticleVectorEntity(
            article = article,
            vector = message.denseVectors
        )

        articleVectorRepository.save(articleVector)


        val keywordEntities:List<ArticleKeyWordEntity> = message.keywords
            .map { ArticleKeyWordEntity(article, it.keyword,it.score) }

        articleKeywordRepository.saveAll(keywordEntities)


        article.status = ArticleStatus.COMPLETED
    }

    @Transactional
    override fun failEmbeddingArticle(articleId: Long) {
        val article:ArticleEntity = articleRepository.findArticleById(articleId)?:return
        article.status=ArticleStatus.FAILED
    }
}