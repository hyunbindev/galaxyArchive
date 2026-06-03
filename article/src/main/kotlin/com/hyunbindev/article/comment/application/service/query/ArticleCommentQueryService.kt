package com.hyunbindev.article.comment.application.service.query

import com.hyunbindev.article.comment.adapter.out.CommentRepository
import com.hyunbindev.article.comment.data.ArticleCommentDto
import com.hyunbindev.article.comment.domain.CommentEntity
import com.hyunbindev.article.comment.port.inbound.ArticleCommentQueryUseCase
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
internal class ArticleCommentQueryService(
    private val commentRepository: CommentRepository
): ArticleCommentQueryUseCase {
    override fun getCommentsByArticleId(articleId:Long):List<ArticleCommentDto>{
        val commentEntities = commentRepository.findCommentListByArticleId(articleId)

        val comments = commentEntities.map{
            ArticleCommentDto(
                id = it.id,
                authorId = it.authorId,
                createdAt = it.created,
                text = it.text,
                parentId = it.parent?.id,
                isDeleted = it.isDeleted
            )
        }

        return comments
    }
}