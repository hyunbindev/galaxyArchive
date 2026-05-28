package com.hyunbindev.infrastructure.article.event.kafaka

import com.hyunbindev.article.image.port.`in`.ArticleImageManageUseCase
import com.hyunbindev.article.article.data.ArticleCreateEvent
import com.hyunbindev.article.article.port.`in`.ArticleEventListener
import com.hyunbindev.article.embedding.port.`in`.CreateArticleVectorUseCase

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ArticleKafkaListener(
    private val createArticleVectorUseCase: CreateArticleVectorUseCase,
    private val articleImageManageUseCase: ArticleImageManageUseCase
): ArticleEventListener {
    @KafkaListener(
        topics = ["article-events"],
        groupId = "vector-group",
    )
    override fun onArticleCreated(event: ArticleCreateEvent) {
        createArticleVectorUseCase.createArticleVector(event.articleId)
        articleImageManageUseCase.linkImageToArticle(event.articleId,event.imageUuids)
    }
}