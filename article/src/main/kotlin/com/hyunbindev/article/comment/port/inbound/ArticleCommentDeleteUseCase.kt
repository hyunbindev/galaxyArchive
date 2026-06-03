package com.hyunbindev.article.comment.port.inbound

import java.util.UUID

interface ArticleCommentDeleteUseCase {
    fun deleteComment(authorId: UUID, commentId:Long)
}