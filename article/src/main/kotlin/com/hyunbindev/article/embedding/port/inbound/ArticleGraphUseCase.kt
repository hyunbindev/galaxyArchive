package com.hyunbindev.article.embedding.port.inbound

import com.hyunbindev.article.embedding.data.ArticleGraphDto

interface ArticleGraphUseCase {
    fun getAllArticleGraph(): ArticleGraphDto
}