package com.hyunbindev.article.article.adapter.outbound

import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.domain.ArticleKeyWordEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleKeywordRepository: JpaRepository<ArticleKeyWordEntity, Long> {

    fun findAllByArticleOrderBySimilarityDesc(articleEntity: ArticleEntity): List<ArticleKeyWordEntity>

    fun findAllByArticleIdInOrderBySimilarityDesc(articleIds: List<Long>): List<ArticleKeyWordEntity>
}