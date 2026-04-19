package com.hyunbindev.article.domain.vector.articlegraph

import com.hyunbindev.article.data.articlegraph.ArticleGraphDto

interface ArticleGraph {
    fun getArticleGraph(edges:List<VectorEdge>): ArticleGraphDto
}