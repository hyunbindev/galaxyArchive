package com.hyunbindev.article.application.service.vector.query

import com.hyunbindev.article.application.port.ArticleGraphUserCase
import com.hyunbindev.article.domain.graph.VectorEdge
import com.hyunbindev.article.domain.graph.VectorGraph
import com.hyunbindev.article.domain.repository.ArticleVectorRepository
import org.springframework.stereotype.Service

@Service
class ArticleVectorGraphService(
    private val articleVectorRepository: ArticleVectorRepository
): ArticleGraphUserCase {
    override fun getAllArticleGraph():List<VectorEdge>{
        val edges = articleVectorRepository.findAllEdgesOrderByWeightDESC()
            .map { VectorEdge(it.u,it.v,it.w) }
        return VectorGraph(edges).getMinimumSpanningTree()
    }
}