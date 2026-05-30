package com.hyunbindev.article.article.data

import com.hyunbindev.article.article.domain.ArticleEntity
import java.time.LocalDateTime
import java.util.UUID

class ArticleDto {
    data class CreateRequest(
        val title: String,
        val text: String,
        val rawText: String,
        val imageUuids: List<UUID>
    )

    data class Response(
        val id:Long?,
        val title: String,
        val text: String,
        val authorId: UUID,
        val createdAt: LocalDateTime
    ){
        companion object{
            fun from(entity: ArticleEntity): Response{
                return Response(
                    id = entity.id,
                    title = entity.title,
                    text = entity.text,
                    authorId = entity.authorId,
                    createdAt = entity.createdAt
                )
            }
        }
    }
}