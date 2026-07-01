package com.hyunbindev.article.embedding.application.service.command

import com.hyunbindev.article.article.adapter.outbound.ArticleRepository
import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.domain.ArticleStatus
import com.hyunbindev.article.embedding.data.EmbeddingCompletedEvent
import com.hyunbindev.article.embedding.port.event.inbound.ArticleEmbeddingCompleteEventHandler

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
internal class AfterEmbeddingArticleService(
    private val articleRepository: ArticleRepository,
): ArticleEmbeddingCompleteEventHandler {

    @Transactional
    override fun handle(message: EmbeddingCompletedEvent) {
        //게시글이 없을 경우 로직 종료
        val article:ArticleEntity = articleRepository.findArticleById(message.articleId)?:return;

        when(message.status){
            "COMPLETED"->{article.status = ArticleStatus.COMPLETED}
            "FAILED"->{article.status = ArticleStatus.FAILED}
        }
    }
}