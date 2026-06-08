package com.hyunbindev.article.article.data

import com.hyunbindev.article.article.adapter.outbound.ArticleSummary
import com.hyunbindev.article.article.domain.ArticleEntity
import java.time.LocalDateTime

data class ArticleSummaryPageDto(
    val articles: List<ArticleSummaryDto>,
    val size:Int,
    val hasNextPage: Boolean,
    val cursorArticleId:Long?,
)


data class ArticleSummaryDto(
    val id:Long,
    val title:String,
    val description:String,
    val createdAt: LocalDateTime,
    val commentCount:Long=0L,
){
    companion object{
        fun from(projection: ArticleSummary):ArticleSummaryDto{
            return ArticleSummaryDto(
                requireNotNull(projection.id) { "article entity is not persistent" },
                title = projection.title,
                description = projection.text,
                createdAt = projection.createdAt,
            )
        }
    }
}