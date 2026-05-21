package com.hyunbindev.article.application.port

import com.hyunbindev.article.data.articlegraph.ArticleGraphDto

interface ArticleGraphUseCase {
    fun getAllArticleGraph(): ArticleGraphDto
}