package com.hyunbindev.infrastructure.article.event.kafka

import com.hyunbindev.article.article.data.ArticleCreateEvent
import com.hyunbindev.article.article.port.event.outbound.ArticleEventPublishPort
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class ArticleKafkaPublisher(
    private val kafkaTemplate: KafkaTemplate<String, ArticleCreateEvent>
): ArticleEventPublishPort {
    private val logger = LoggerFactory.getLogger(ArticleKafkaPublisher::class.java)
    override fun publishCreateEvent(event: ArticleCreateEvent) {
        try {
            val response = kafkaTemplate.send("article-created",  event.articleId.toString(), event)
            response.get(3, TimeUnit.SECONDS)
        }catch (e: Exception){
            logger.error(e.message, e)
            //TODO-재시도 로직
            throw e;
        }
    }
}