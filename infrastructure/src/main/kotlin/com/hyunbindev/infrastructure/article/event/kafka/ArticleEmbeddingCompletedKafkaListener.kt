package com.hyunbindev.infrastructure.article.event.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.hyunbindev.article.embedding.data.EmbeddingCompletedEvent
import com.hyunbindev.article.embedding.data.EmbeddingFailEvent
import com.hyunbindev.article.embedding.port.inbound.EmbeddingArticleUseCase
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ArticleEmbeddingCompletedKafkaListener(
    private val embeddingArticleUseCase:EmbeddingArticleUseCase,
    private val objectMapper: ObjectMapper,
)
{
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["embedding-complete"],
        groupId = "article-service",
        containerFactory = "stringKafkaListenerFactory"
    )
    fun onArticleEmbedded(eventString:String){
        val event = objectMapper.readValue(
            eventString,
            EmbeddingCompletedEvent::class.java
        )
        logger.info(event.toString())
        embeddingArticleUseCase.afterEmbeddingArticleHandler(event)
    }
}