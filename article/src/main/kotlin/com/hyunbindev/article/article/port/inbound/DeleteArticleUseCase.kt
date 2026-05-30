package com.hyunbindev.article.article.port.inbound

import java.util.UUID

interface DeleteArticleUseCase {
    fun deleteArticle(userId: UUID, articleId:Long, articleTitle:String)
}