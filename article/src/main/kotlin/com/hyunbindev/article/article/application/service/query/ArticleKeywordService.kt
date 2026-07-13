package com.hyunbindev.article.article.application.service.query

import com.hyunbindev.article.article.adapter.outbound.ArticleKeywordProjection
import com.hyunbindev.article.article.adapter.outbound.ArticleKeywordRepository
import com.hyunbindev.article.article.data.ArticleKeywordDto
import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.port.usecase.inbound.ArticleKeywordQueryUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly=true)
internal class ArticleKeywordService(
    private val articleKeywordRepository: ArticleKeywordRepository
): ArticleKeywordQueryUseCase {
    override fun getKeywordsByArticleIds(articleIds:List<Long>,limit:Int):List<ArticleKeywordDto>{
        val keywords: List<ArticleKeywordProjection> = articleKeywordRepository.findTopByArticleIdInOrderBySimilarityDesc(articleIds,limit)
        return keywords.map{ ArticleKeywordDto(it.articleId, it.keyword,it.similarity) }
    }
}