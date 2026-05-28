package com.hyunbindev.article.article.application.service.command.create

import com.hyunbindev.article.article.port.`in`.CreateArticleUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.data.ArticleCreateEvent
import com.hyunbindev.article.article.port.out.ArticleEventPublisher
import com.hyunbindev.article.article.adapter.out.ArticleRepository
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
        articleCreateEvent.publishCreateEvent(ArticleCreateEvent.from(article,req.imageUuids))
        return article.id!!
    }
}