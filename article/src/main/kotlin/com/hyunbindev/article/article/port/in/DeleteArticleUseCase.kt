package com.hyunbindev.article.article.port.`in`

import java.util.UUID

interface DeleteArticleUseCase {
    fun deleteArticle(userId: UUID, articleId:Long, articleTitle:String)
}