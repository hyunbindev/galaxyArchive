package com.hyunbindev.article.comment.application.service.command

import com.hyunbindev.article.article.port.inbound.ArticleQueryUseCase
import com.hyunbindev.article.comment.adapter.out.CommentRepository
import com.hyunbindev.article.comment.domain.CommentEntity
import com.hyunbindev.article.comment.port.inbound.ArticleCommentCreateUseCase
import com.hyunbindev.article.global.exception.ArticleException
import com.hyunbindev.article.global.exception.constant.ArticleCommentExceptionCode
import com.hyunbindev.article.global.exception.constant.ArticleExceptionCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class ArticleCommentCreateService(
    private val commentRepository: CommentRepository,
    private val articleQueryUseCase: ArticleQueryUseCase,
): ArticleCommentCreateUseCase {
    @Transactional
    override fun createArticleComment(authorId: UUID, articleId:Long, text:String){
        if(!articleQueryUseCase.isArticleExist(articleId))
            throw ArticleException(ArticleExceptionCode.ARTICLE_NOT_FOUND)

        val comment:CommentEntity = CommentEntity(authorId=authorId, articleId=articleId, text=text)

        commentRepository.save(comment)
    }

    @Transactional
    override fun createArticleRecomment(authorId: UUID, articleId:Long, parentCommentId:Long ,text:String){
        if(!articleQueryUseCase.isArticleExist(articleId))
            throw ArticleException(ArticleExceptionCode.ARTICLE_NOT_FOUND)

        val parentComment:CommentEntity = commentRepository.findArticleCommentById(parentCommentId)
            ?:throw ArticleException(ArticleCommentExceptionCode.COMMENT_NOT_FOUND)

        if(parentComment.articleId != articleId){
            throw ArticleException(ArticleCommentExceptionCode.COMMENT_BAD_REQUEST)
        }

        val comment:CommentEntity = CommentEntity(authorId=authorId, articleId=articleId, parent = parentComment , text=text)

        commentRepository.save(comment)
    }
}