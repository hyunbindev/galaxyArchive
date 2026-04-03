package com.hyunbindev.infrastructure.article.kafaka

import com.hyunbindev.article.application.service.vector.command.ArticleVectorService
import com.hyunbindev.article.domain.event.create.ArticleCreateEvent
import com.hyunbindev.article.domain.event.create.ArticleEventListener
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
internal class ArticleKafkaListener(
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