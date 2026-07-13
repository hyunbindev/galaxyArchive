package com.hyunbindev.article.article.adapter.outbound

import com.hyunbindev.article.article.domain.ArticleEntity
import com.hyunbindev.article.article.domain.ArticleKeyWordEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleKeywordRepository: JpaRepository<ArticleKeyWordEntity, Long> {

    fun findAllByArticleOrderBySimilarityDesc(articleEntity: ArticleEntity): List<ArticleKeyWordEntity>

    fun findAllByArticleIdInOrderBySimilarityDesc(articleIds: List<Long>): List<ArticleKeyWordEntity>

    @Query(
        value = """
        SELECT 
            article_id AS articleId,
            keyword AS keyword,
            similarity AS similarity
        FROM article_key_word_entity
        WHERE article_id IN (:articleIds)
        ORDER BY similarity DESC
        LIMIT :limit
    """,nativeQuery = true)
    fun findTopByArticleIdInOrderBySimilarityDesc(articleIds: Collection<Long>, limit: Int = 100): List<ArticleKeywordProjection>
}

interface ArticleKeywordProjection{
    val articleId:Long
    val keyword:String
    val similarity:Float
}