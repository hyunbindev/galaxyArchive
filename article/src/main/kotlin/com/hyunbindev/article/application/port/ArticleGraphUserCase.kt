package com.hyunbindev.article.application.port

import com.hyunbindev.article.domain.graph.VectorEdge

interface ArticleGraphUserCase {
    fun getAllArticleGraph(): List<VectorEdge>
}