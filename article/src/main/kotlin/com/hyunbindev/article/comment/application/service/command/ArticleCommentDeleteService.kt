package com.hyunbindev.article.comment.application.service.command

import com.hyunbindev.article.comment.adapter.out.CommentRepository
import com.hyunbindev.article.comment.domain.CommentEntity
import com.hyunbindev.article.comment.port.inbound.ArticleCommentDeleteUseCase
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ArticleCommentExceptionCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
internal class ArticleCommentDeleteService(
    private val commentRepository: CommentRepository,
): ArticleCommentDeleteUseCase {

    @Transactional
    override fun deleteComment(authorId: UUID, commentId:Long){
        val comment: CommentEntity = commentRepository.findArticleCommentById(commentId)
            ?:throw ArticleException(ArticleCommentExceptionCode.COMMENT_NOT_FOUND)

        if(comment.authorId != authorId) throw ArticleException(ArticleCommentExceptionCode.COMMENT_FORBIDDEN)

        comment.delete()
    }
}