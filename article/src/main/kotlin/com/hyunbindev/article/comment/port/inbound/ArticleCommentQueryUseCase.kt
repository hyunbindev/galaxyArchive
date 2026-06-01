package com.hyunbindev.article.comment.port.inbound

import com.hyunbindev.article.comment.data.ArticleCommentDto

interface ArticleCommentQueryUseCase {
    fun getCommentsByArticleId(articleId:Long):List<ArticleCommentDto>
}