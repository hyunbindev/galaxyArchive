package com.hyunbindev.article.application.port

import java.util.UUID

interface DeleteArticleUseCase {
    fun deleteArticle(userId: UUID, articleId:Long, articleTitle:String)
}