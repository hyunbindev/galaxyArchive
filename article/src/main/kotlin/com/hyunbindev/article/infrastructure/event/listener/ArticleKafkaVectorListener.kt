package com.hyunbindev.article.infrastructure.event.listener

import com.hyunbindev.article.application.service.vector.command.ArticleVectorService
import com.hyunbindev.article.domain.event.create.ArticleCreateEvent
import com.hyunbindev.article.domain.event.create.ArticleCreateEventListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ArticleKafkaVectorListener(
    private val articleVectorService: ArticleVectorService,
) : ArticleCreateEventListener {
    private val logger = LoggerFactory.getLogger(ArticleKafkaVectorListener::class.java)
    @KafkaListener(
        topics = ["article-events"],
        groupId = "vector-group",
    )
    override fun onArticleCreated(event: ArticleCreateEvent) {
        logger.info("Received article ${event.articleId}")
        articleVectorService.createArticleVector(event.articleId)
    }
}