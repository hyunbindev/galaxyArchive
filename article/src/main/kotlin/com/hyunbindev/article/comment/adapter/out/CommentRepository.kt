package com.hyunbindev.article.comment.adapter.out

import com.hyunbindev.article.comment.domain.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<CommentEntity, Long> {
}