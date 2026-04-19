package com.hyunbindev.article.application.service.vector.query

import com.hyunbindev.article.application.port.ArticleGraphUseCase
import com.hyunbindev.article.data.articlegraph.ArticleGraphDto
import com.hyunbindev.article.domain.vector.articlegraph.ArticleGraph
import com.hyunbindev.article.domain.vector.articlegraph.KruskalArticleGraphService
import com.hyunbindev.article.domain.vector.articlegraph.VectorEdge
import com.hyunbindev.article.domain.vector.articlegraph.VectorGraph
import com.hyunbindev.article.domain.vector.repository.ArticleVectorRepository
import org.springframework.stereotype.Service

@Service
class ArticleVectorGraphService(
    private val articleVectorRepository: ArticleVectorRepository,
    private val articleGraph: ArticleGraph = KruskalArticleGraphService()
): ArticleGraphUseCase {
    override fun getAllArticleGraph(): ArticleGraphDto{
        val edges = articleVectorRepository.findAllEdgesOrderByWeightASC()
            .map { VectorEdge(it.u_title,it.v_title,it.u,it.v,it.w) }
        return articleGraph.getArticleGraph(edges)
    }
}