package com.hyunbindev.article.article.port.usecase.inbound

import java.util.UUID

interface ArticleStatsQueryUseCase {
    fun getArticleCountByAuthorId(authorId: UUID):Int
}