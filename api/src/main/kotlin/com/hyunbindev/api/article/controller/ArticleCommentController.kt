package com.hyunbindev.api.article.controller

import com.hyunbindev.api.article.composition.ArticleCommentComposition
import com.hyunbindev.api.article.data.ArticleCommentCompositionDto
import com.hyunbindev.api.article.data.ArticleCreateRequestDto
import com.hyunbindev.article.comment.port.inbound.ArticleCommentCreateUseCase
import com.hyunbindev.article.comment.port.inbound.ArticleCommentDeleteUseCase
import com.hyunbindev.common.auth.LoginUserId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/articles/{articleId}/comments")
class ArticleCommentController(
    private val articleCommentCreateUseCase: ArticleCommentCreateUseCase,
    private val articleCommentDeleteUseCase: ArticleCommentDeleteUseCase,
    private val articleCommentQueryComposition: ArticleCommentComposition
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createComment(
        @LoginUserId userId: UUID,
        @PathVariable articleId: Long,
        @RequestParam(required = false) parentId: Long?,
        @RequestBody articleCommentDto: ArticleCreateRequestDto
    ) {

        when (parentId) {
            null -> articleCommentCreateUseCase.createArticleComment(userId, articleId, articleCommentDto.text)
            else -> articleCommentCreateUseCase.createArticleRecomment(
                userId,
                articleId,
                parentId,
                articleCommentDto.text
            )
        }
    }

    @GetMapping
    fun getComments(@PathVariable articleId: Long): List<ArticleCommentCompositionDto> {
        return articleCommentQueryComposition.getArticleComment(articleId)
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteComment(@LoginUserId userId: UUID, @PathVariable commentId: Long) {
        articleCommentDeleteUseCase.deleteComment(userId, commentId)
    }
}