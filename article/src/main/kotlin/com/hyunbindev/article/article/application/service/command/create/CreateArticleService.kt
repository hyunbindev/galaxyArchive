package com.hyunbindev.article.article.application.service.command.create

import com.hyunbindev.article.article.port.usecase.inbound.CreateArticleUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.data.ArticleCreateEvent
import com.hyunbindev.article.article.adapter.outbound.ArticleRepository
import com.hyunbindev.article.article.port.event.outbound.ArticleEventPublishPort
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ArticleExceptionCode
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.util.UUID

@Service
internal class CreateArticleService(
    private val articleRepository: ArticleRepository,
    private val articleEventPublishPort: ArticleEventPublishPort,
    private val applicationEventPublisher: ApplicationEventPublisher,
): CreateArticleUseCase {
    @Transactional
    override fun createArticle(userId: UUID, req: ArticleDto.CreateRequest):Long{
        val article: ArticleEntity = articleRepository.save(ArticleEntity.from(userId, req))

        val articleId = article.id
            ?:throw ArticleException(ArticleExceptionCode.ARTICLE_INTERNAL_ERROR,"Not persisted Article")

        applicationEventPublisher.publishEvent(ArticleCreateEvent.from(article, req.imageUuids))

        return articleId
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun publishArticleCreateEvent(event:ArticleCreateEvent){
        articleEventPublishPort.publishCreateEvent(event)
    }
}