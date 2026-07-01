package com.hyunbindev.article.article.port.usecase.inbound

import com.hyunbindev.article.article.data.ArticleDto
import java.util.UUID

interface CreateArticleUseCase {
    fun createArticle(userId: UUID, req: ArticleDto.CreateRequest):Long
}