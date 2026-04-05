package com.hyunbindev.article.application.service.article.command.create

import com.hyunbindev.article.application.port.CreateArticleUseCase
import com.hyunbindev.article.data.article.ArticleDto
import com.hyunbindev.article.domain.article.ArticleEntity
import com.hyunbindev.article.domain.article.event.ArticleCreateEvent
import com.hyunbindev.article.domain.article.event.ArticleEventPublisher
import com.hyunbindev.article.domain.article.repository.ArticleRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class CreateArticleService(
    private val articleRepository: ArticleRepository,
    private val articleCreateEvent: ArticleEventPublisher
): CreateArticleUseCase {
    @Transactional
    override fun createArticle(userId: UUID, req: ArticleDto.CreateRequest):Long{
        val article: ArticleEntity = articleRepository.save(ArticleEntity.from(userId, req))
        articleCreateEvent.publishCreateEvent(ArticleCreateEvent.from(article))
        return article.id!!
    }
}