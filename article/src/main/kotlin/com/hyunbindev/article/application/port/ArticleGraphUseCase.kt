package com.hyunbindev.article.application.port

import com.hyunbindev.article.domain.vector.VectorEdge

interface ArticleGraphUseCase {
    fun getAllArticleGraph(): List<VectorEdge>
}