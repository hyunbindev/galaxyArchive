package com.hyunbindev.article.application.port

import com.hyunbindev.article.data.article.ArticleDto

interface ArticleQueryUseCase {
    fun getArticle(id:Long): ArticleDto.Response
}