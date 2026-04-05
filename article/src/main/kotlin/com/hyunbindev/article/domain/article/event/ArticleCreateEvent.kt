package com.hyunbindev.article.domain.article.event

import com.fasterxml.jackson.annotation.JsonFormat
import com.hyunbindev.article.domain.article.entity.ArticleEntity
import java.time.LocalDateTime
import java.util.UUID

data class ArticleCreateEvent(
    val articleId:Long,
    val authorId: UUID,
    val traceId:String = UUID.randomUUID().toString(),
    @field:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss",
        timezone = "Asia/Seoul"
    )
    val occurredAt: LocalDateTime = LocalDateTime.now()
){
    companion object{
        fun from(article: ArticleEntity): ArticleCreateEvent{
            val id = article.id ?: throw IllegalArgumentException("article id is null")

            return ArticleCreateEvent(
                articleId = id,
                authorId = article.authorId,
            )
        }
    }
}
