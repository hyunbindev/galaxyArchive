package com.hyunbindev.article.embedding.port.event.outbound

import java.util.UUID

interface ClusterCreateEventPublishPort {
    fun publish(userId: UUID)
}