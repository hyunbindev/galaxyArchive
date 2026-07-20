package com.hyunbindev.article.article.data

import com.hyunbindev.article.article.adapter.outbound.ArticleKeywordProjection
import com.hyunbindev.article.article.domain.ArticleKeyWordEntity
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ArticleExceptionCode
import org.slf4j.LoggerFactory

data class ArticleKeywordDto(
    val articleId:Long,
    val keyword:String,
    val similarity: Float
){
    companion object{

        fun from(entity: ArticleKeyWordEntity):ArticleKeywordDto{
            val articleId:Long = entity.article.id
                ?:throw ArticleException(ArticleExceptionCode.ARTICLE_INTERNAL_ERROR, "not persisted article Entity")
            return ArticleKeywordDto(articleId, entity.keyword, entity.similarity)
        }

        fun from(projection: ArticleKeywordProjection):ArticleKeywordDto{
            return ArticleKeywordDto(projection.articleId, projection.keyword, projection.similarity)
        }
    }
}