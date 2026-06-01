package com.hyunbindev.article.article.port.inbound

import com.hyunbindev.article.article.data.ArticleDto

interface ArticleQueryUseCase {
    fun getArticle(id:Long): ArticleDto.Response
    fun isArticleExist(id:Long):Boolean
}