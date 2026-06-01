package com.hyunbindev.article.comment.port.inbound

import java.util.UUID

interface ArticleCommentCreateUseCase {
    fun createArticleComment(authorId: UUID, articleId:Long, text:String)
    fun createArticleRecomment(authorId: UUID, articleId:Long, parentCommentId:Long ,text:String)
}