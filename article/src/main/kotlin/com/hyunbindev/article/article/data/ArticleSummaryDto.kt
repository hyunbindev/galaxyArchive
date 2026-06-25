package com.hyunbindev.article.article.data

import com.hyunbindev.article.article.adapter.outbound.ArticleSummary
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
    val commentsCount:Int=0,
    val keywords:List<String>
){
    companion object{
        fun from(projection: ArticleSummary, commentCount:Int?, keywords:List<String>):ArticleSummaryDto{
            return ArticleSummaryDto(
                requireNotNull(projection.id) { "article entity is not persistent" },
                title = projection.title,
                description = projection.text,
                createdAt = projection.createdAt,
                commentsCount = commentCount?:0,
                keywords = keywords
            )
        }
    }
}