package com.hyunbindev.article.article.data

import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.domain.ArticleKeyWordEntity
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
        val keywords:List<String>,
        val createdAt: LocalDateTime
    ){
        companion object{
            fun from(articleEntity: ArticleEntity, keywordEntity: List<ArticleKeyWordEntity>): Response{
                return Response(
                    id = articleEntity.id,
                    title = articleEntity.title,
                    text = articleEntity.text,
                    authorId = articleEntity.authorId,
                    createdAt = articleEntity.createdAt,
                    keywords = keywordEntity.map{ it.keyword }
                )
            }
        }
    }
}