package com.hyunbindev.article.article.port.`in`

import com.hyunbindev.article.article.data.ArticleDto

interface ArticleQueryUseCase {
    fun getArticle(id:Long): ArticleDto.Response
}