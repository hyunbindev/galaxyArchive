package com.hyunbindev.infrastructure.article.event.kafka

import com.hyunbindev.article.image.port.inbound.ArticleImageManageUseCase
import com.hyunbindev.article.article.data.ArticleCreateEvent
import com.hyunbindev.article.article.port.event.inbound.ArticleCreatedEventHandler

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ArticleCreatedEventKafkaListener(
    private val articleImageManageUseCase: ArticleImageManageUseCase
): ArticleCreatedEventHandler {
    @KafkaListener(
        topics = ["article-created"],
        groupId = "article-service",
    )
    override fun handle(event: ArticleCreateEvent) {
        articleImageManageUseCase.linkImageToArticle(event.articleId,event.imageUuids)
    }
}