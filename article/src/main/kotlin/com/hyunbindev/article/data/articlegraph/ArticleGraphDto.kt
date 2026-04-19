package com.hyunbindev.article.data.articlegraph

import com.hyunbindev.article.domain.vector.articlegraph.VectorEdge

data class ArticleGraphDto(
    val clusters:Map<Long,List<Long>>,
    val edges: List<VectorEdge>
)