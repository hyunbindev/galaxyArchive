package com.hyunbindev.infrastructure.kafka.configuration

import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class KafkaListenerFactory {

    @Bean
    fun stringKafkaListenerFactory(
        kafkaProperties: KafkaProperties
    ): ConcurrentKafkaListenerContainerFactory<String, String> {

        val props = kafkaProperties.buildConsumerProperties(null)

        val consumerFactory =
            DefaultKafkaConsumerFactory(
                props,
                StringDeserializer(),
                StringDeserializer()
            )

        return ConcurrentKafkaListenerContainerFactory<String, String>()
            .apply {
                this.consumerFactory = consumerFactory
            }
    }
}