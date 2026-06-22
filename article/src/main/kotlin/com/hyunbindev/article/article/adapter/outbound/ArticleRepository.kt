package com.hyunbindev.article.article.adapter.outbound

import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.article.article.domain.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, Long> {
    @Query("SELECT a FROM ArticleEntity a WHERE a.id=:id AND a.isDeleted = false")
    fun findArticleById(id: Long): ArticleEntity?

    @Query("SELECT a FROM ArticleEntity a WHERE a.id=:id AND a.isDeleted = true")
    fun findArticleByIdWithDeleted(id: Long): ArticleEntity?

    @Query(
        value = """
        SELECT
         a.id AS id,
         a.title AS title,
         LEFT(COALESCE(a.raw_text,''), :textLength) AS text,
         a.created_at AS createdAt,
         a.author_id AS authorId
        FROM article_entity a
        WHERE a.author_id = :authorId
        AND a.is_deleted = false
        AND (:cursorId IS NULL OR a.id < :cursorId)
        ORDER BY a.created_at DESC
        LIMIT :size
    """, nativeQuery = true
    )
    fun findByArticleSummaryByUserIdByCursor(
        size: Int,
        cursorId: Long?,
        authorId: UUID,
        textLength: Int
    ): List<ArticleSummary>

    @Query("""
        SELECT count(article) FROM ArticleEntity article 
        WHERE article.authorId=:authorId
        AND article.isDeleted = false
    """)
    fun countByAuthorId(authorId: UUID): Int
}

interface ArticleSummary {
    val id: Long
    val title: String
    val text: String
    val createdAt: LocalDateTime
    val authorId: UUID
}