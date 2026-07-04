package com.hyunbindev.infrastructure.article.event.kafka

import com.hyunbindev.article.article.data.ArticleCreateEvent
import com.hyunbindev.article.embedding.port.event.outbound.ClusterCreateEventPublishPort
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.TimeUnit

@Component
class ClusterCreateKafkaPublisher(
    private val kafkaTemplate: KafkaTemplate<String, UUID>
): ClusterCreateEventPublishPort {
    private val logger = LoggerFactory.getLogger(ClusterCreateKafkaPublisher::class.java)
    override fun publish(userId: UUID) {
        try{
            val response = kafkaTemplate.send("article-user-cluster", userId.toString(), userId)
            response.get(3, TimeUnit.SECONDS)
        }catch(e: Exception){
            logger.error(e.message, e)
            throw e
        }
    }
}