package com.hyunbindev.article.domain.vector.repository

import com.hyunbindev.article.domain.vector.ArticleVectorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ArticleVectorRepository : JpaRepository<ArticleVectorEntity, Long> {
    @Query(
        """
                SELECT
                    a.article_id AS u,
                    b.article_id AS v,
                    a.vector <=> b.vector AS w
                FROM article_vector_entity a
                JOIN article_vector_entity b ON a.article_id < b.article_id
                WHERE a.vector <=> b.vector < 0.4
                ORDER BY w ASC;
               """, nativeQuery = true)
    fun findAllEdgesOrderByWeightASC(): List<ArticleEdgeProjection>
}

interface ArticleEdgeProjection{
    val u:Long
    val v:Long
    val w:Float
}