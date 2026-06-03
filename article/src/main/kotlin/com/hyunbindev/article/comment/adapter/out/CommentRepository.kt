package com.hyunbindev.article.comment.adapter.out

import com.hyunbindev.article.comment.domain.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<CommentEntity, Long> {
    @Query("SELECT a FROM CommentEntity a WHERE a.id=:id AND a.isDeleted = false")
    fun findArticleCommentById(id: Long): CommentEntity?

    @Query("""
        SELECT c From CommentEntity c
        WHERE c.articleId = :articleId
        AND NOT (c.parent IS NOT NULL AND c.isDeleted = true)
        ORDER BY c.created DESC
        """)
    fun findCommentListByArticleId(articleId:Long): List<CommentEntity>
}