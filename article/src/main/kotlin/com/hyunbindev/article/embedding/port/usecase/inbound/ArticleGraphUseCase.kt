package com.hyunbindev.article.embedding.port.usecase.inbound

import com.hyunbindev.article.embedding.data.ArticleGraphDto

interface ArticleGraphUseCase {
    fun getAllArticleGraph(): ArticleGraphDto
}