package com.hyunbindev.article.application.port

import com.hyunbindev.article.data.article.ArticleDto
import java.util.UUID

interface CreateArticleUseCase {
    fun createArticle(userId: UUID, req: ArticleDto.CreateRequest):Long
}