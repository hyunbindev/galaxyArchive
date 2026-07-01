package com.hyunbindev.article.article.application.service.command.create

import com.hyunbindev.article.article.port.usecase.inbound.CreateArticleUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.data.ArticleCreateEvent
import com.hyunbindev.article.article.adapter.outbound.ArticleRepository
import com.hyunbindev.article.article.port.event.outbound.ArticleEventPublishPort
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class CreateArticleService(
    private val articleRepository: ArticleRepository,
    private val articleEventPublishPort: ArticleEventPublishPort
): CreateArticleUseCase {
    @Transactional
    override fun createArticle(userId: UUID, req: ArticleDto.CreateRequest):Long{
        val article: ArticleEntity = articleRepository.save(ArticleEntity.from(userId, req))
        articleEventPublishPort.publishCreateEvent(ArticleCreateEvent.from(article,req.imageUuids))
        return article.id!!
    }
}