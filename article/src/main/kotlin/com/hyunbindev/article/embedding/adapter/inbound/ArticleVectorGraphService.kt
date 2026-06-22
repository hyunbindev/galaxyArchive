package com.hyunbindev.article.embedding.adapter.inbound

import com.hyunbindev.article.embedding.port.inbound.ArticleGraphUseCase
import com.hyunbindev.article.embedding.data.ArticleGraphDto
import com.hyunbindev.article.embedding.domain.getArticleGraph
import com.hyunbindev.article.embedding.adapter.outbound.ArticleVectorRepository
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