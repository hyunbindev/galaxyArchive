package com.hyunbindev.article.article.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.hyunbindev.article.article.domain.ArticleEntity
import java.time.LocalDateTime
import java.util.UUID

data class ArticleCreateEvent(
    val articleId:Long,
    val authorId: UUID,
    val traceId:String = UUID.randomUUID().toString(),
    val imageUuids: List<UUID>,
    @field:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss",
        timezone = "Asia/Seoul"
    )
    val occurredAt: LocalDateTime = LocalDateTime.now()
){
    companion object{
        fun from(article: ArticleEntity, imageUuids:List<UUID>): ArticleCreateEvent{
            val id = article.id ?: throw IllegalArgumentException("Article Id is null : {${article.id}}")
            return ArticleCreateEvent(
                articleId = id,
                authorId = article.authorId,
                imageUuids = imageUuids,
            )
        }
    }
}