package com.hyunbindev.infrastructure.article.kafaka

import com.hyunbindev.article.domain.event.create.ArticleCreateEvent
import com.hyunbindev.article.domain.event.create.ArticleEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
internal class ArticleKafkaPublisher(
    private val kafkaTemplate: KafkaTemplate<String, ArticleCreateEvent>
): ArticleEventPublisher {
    private val logger = LoggerFactory.getLogger(ArticleKafkaPublisher::class.java)
    override fun publishCreateEvent(event: ArticleCreateEvent) {
        try {
            val response = kafkaTemplate.send("article-events",  event.articleId.toString(), event)
            response.get(3, TimeUnit.SECONDS)
        }catch (e: Exception){
            logger.error(e.message, e)
            throw e;
        }
    }
}