package com.hyunbindev.article.article.port.inbound

import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.data.ArticleSummaryPageDto
import java.util.UUID

interface ArticleQueryUseCase {
    fun getArticle(id:Long): ArticleDto.Response
    fun isArticleExist(id:Long):Boolean
    fun getArticleSummaryPageByCursorAndAuthor(authorId: UUID, cursorArticleId:Long?, size:Int): ArticleSummaryPageDto
}