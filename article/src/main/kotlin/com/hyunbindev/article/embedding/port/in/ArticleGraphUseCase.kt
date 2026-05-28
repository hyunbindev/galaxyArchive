package com.hyunbindev.article.embedding.port.`in`

import com.hyunbindev.article.embedding.data.ArticleGraphDto

interface ArticleGraphUseCase {
    fun getAllArticleGraph(): ArticleGraphDto
}