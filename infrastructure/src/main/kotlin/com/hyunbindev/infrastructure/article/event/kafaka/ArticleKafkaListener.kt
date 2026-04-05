package com.hyunbindev.infrastructure.article.event.kafaka

import com.hyunbindev.article.application.service.vector.command.ArticleVectorService
import com.hyunbindev.article.domain.article.event.ArticleCreateEvent
import com.hyunbindev.article.domain.article.event.ArticleEventListener

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ArticleKafkaListener(
    private val articleVectorService: ArticleVectorService,
): ArticleEventListener {
    @KafkaListener(
        topics = ["article-events"],
        groupId = "vector-group",
    )
    override fun onArticleCreated(event: ArticleCreateEvent) {
        articleVectorService.createArticleVector(event.articleId)
    }
}