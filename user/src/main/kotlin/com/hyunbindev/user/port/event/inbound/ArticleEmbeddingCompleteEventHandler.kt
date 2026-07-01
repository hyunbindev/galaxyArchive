package com.hyunbindev.user.port.event.inbound

import java.util.UUID

interface ArticleEmbeddingCompleteEventHandler {
    fun handle(authorId: UUID)
}