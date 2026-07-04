package com.hyunbindev.article.comment.application.service.query

import com.hyunbindev.article.comment.adapter.outbound.CommentCountProjection
import com.hyunbindev.article.comment.adapter.outbound.CommentRepository
import com.hyunbindev.article.comment.data.ArticleCommentDto
import com.hyunbindev.article.comment.port.inbound.ArticleCommentQueryUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class ArticleCommentQueryService(
    private val commentRepository: CommentRepository
): ArticleCommentQueryUseCase {
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    override fun getCommentCountByArticleIds(articleId:List<Long>):Map<Long,Int>{
        val commentCounts:List<CommentCountProjection> = commentRepository.countCommentsByArticleIds(articleId);
        return commentCounts.associate { it.getArticleId() to it.getCommentCount() }
    }
}