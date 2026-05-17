package com.hyunbindev.article.domain.vector.articlegraph

import com.hyunbindev.article.data.articlegraph.ArticleGraphDto
import com.hyunbindev.article.domain.vector.repository.ArticleEdgeProjection

interface ArticleGraph {
    fun getArticleGraph(edges:List<ArticleEdgeProjection>): ArticleGraphDto
}