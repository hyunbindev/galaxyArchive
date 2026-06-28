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
internal class AfterEmbeddingArticleService(
    private val articleRepository: ArticleRepository,
): EmbeddingArticleUseCase {

    @Transactional
    override fun afterEmbeddingArticleHandler(message: EmbeddingCompletedEvent) {
        //게시글이 없을 경우 로직 종료
        val article:ArticleEntity = articleRepository.findArticleById(message.articleId)?:return;

        when(message.status){
            "COMPLETED"->{article.status = ArticleStatus.COMPLETED}
            "FAILED"->{article.status = ArticleStatus.FAILED}
        }
    }
}