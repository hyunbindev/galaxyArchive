package com.hyunbindev.article.comment.data

import java.time.LocalDateTime
import java.util.UUID

data class ArticleCommentDto(
    val id:Long,
    val authorId: UUID,
    val createdAt: LocalDateTime,
    val text: String,
    val parentId: Long?,
    val isDeleted: Boolean
)