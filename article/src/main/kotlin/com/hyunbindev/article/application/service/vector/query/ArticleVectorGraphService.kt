package com.hyunbindev.article.application.service.vector.query

import com.hyunbindev.article.application.port.ArticleGraphUseCase
import com.hyunbindev.article.domain.vector.VectorEdge
import com.hyunbindev.article.domain.vector.VectorGraph
import com.hyunbindev.article.domain.vector.repository.ArticleVectorRepository
import org.springframework.stereotype.Service

@Service
class ArticleVectorGraphService(
    private val articleVectorRepository: ArticleVectorRepository
): ArticleGraphUseCase {
    override fun getAllArticleGraph():List<VectorEdge>{
        val edges = articleVectorRepository.findAllEdgesOrderByWeightASC()
            .map { VectorEdge(it.u_title,it.v_title,it.u,it.v,it.w) }
        return VectorGraph(edges).getMinimumSpanningTree()
    }
}