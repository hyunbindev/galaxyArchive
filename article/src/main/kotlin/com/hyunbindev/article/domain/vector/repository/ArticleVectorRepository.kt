package com.hyunbindev.article.domain.vector.repository

import com.hyunbindev.article.domain.vector.entity.ArticleVectorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ArticleVectorRepository : JpaRepository<ArticleVectorEntity, Long> {
    @Query(
        """
                SELECT
                    u_article.title AS u_title,
                    v_article.title AS v_title,
                    u_vector.article_id AS u,
                    v_vector.article_id AS v,
                    u_vector.vector <=> v_vector.vector AS w
                FROM article_vector_entity u_vector
                JOIN article_vector_entity v_vector ON u_vector.article_id < v_vector.article_id
                JOIN article_entity u_article ON u_article.id = u_vector.article_id
                JOIN article_entity v_article ON v_article.id = v_vector.article_id
                WHERE u_vector.vector <=> v_vector.vector < 0.4
                ORDER BY w ASC;
               """, nativeQuery = true)
    fun findAllEdgesOrderByWeightASC(): List<ArticleEdgeProjection>

}

interface ArticleEdgeProjection{
    val u_title: String
    val v_title: String
    val u:Long
    val v:Long
    val w:Float
}