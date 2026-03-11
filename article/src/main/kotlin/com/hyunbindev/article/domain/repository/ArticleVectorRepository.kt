package com.hyunbindev.article.domain.repository

import com.hyunbindev.article.domain.entity.ArticleVectorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ArticleVectorRepository : JpaRepository<ArticleVectorEntity, Long> {
    @Query(
        """
                SELECT
                    a.article_id AS u,
                    b.article_id AS v,
                    (1 - (a.vector <=> b.vector)) AS w
                FROM article_vector_entity a
                JOIN article_vector_entity b ON a.article_id < b.article_id
                WHERE (1 - (a.vector <=> b.vector)) >= 0.6
                ORDER BY w DESC;
               """, nativeQuery = true)
    fun findAllEdgesOrderByWeightDESC(): List<ArticleEdgeProjection>
}

interface ArticleEdgeProjection{
    val u:Long
    val v:Long
    val w:Float
}