package com.hyunbindev.article.application.port

import com.hyunbindev.article.data.articlegraph.ArticleGraphDto
import com.hyunbindev.article.domain.vector.articlegraph.VectorEdge

interface ArticleGraphUseCase {
    fun getAllArticleGraph(): ArticleGraphDto
}