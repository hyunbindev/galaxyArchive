package com.hyunbindev.article.application.service.vector.query

import com.hyunbindev.article.application.port.ArticleGraphUseCase
import com.hyunbindev.article.data.articlegraph.ArticleGraphDto
import com.hyunbindev.article.domain.vector.articlegraph.getArticleGraph
import com.hyunbindev.article.domain.vector.repository.ArticleVectorRepository
import org.springframework.stereotype.Service

@Service
class ArticleVectorGraphService(
    private val articleVectorRepository: ArticleVectorRepository,
): ArticleGraphUseCase {
    override fun getAllArticleGraph(): ArticleGraphDto{
        val edges = articleVectorRepository.findAllEdgesOrderByWeightASC()
        return getArticleGraph(edges)
    }
}