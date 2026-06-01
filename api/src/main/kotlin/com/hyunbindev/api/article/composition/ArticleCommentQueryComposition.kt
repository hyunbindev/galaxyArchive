package com.hyunbindev.api.article.composition

import com.hyunbindev.api.article.data.ArticleCommentCompositionDto
import com.hyunbindev.article.comment.data.ArticleCommentDto
import com.hyunbindev.article.comment.port.inbound.ArticleCommentQueryUseCase
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.port.inbound.UserQueryUseCase
import org.springframework.stereotype.Service


@Service
class ArticleCommentQueryComposition(
    private val userQueryUseCase: UserQueryUseCase,
    private val articleCommentQueryUseCase: ArticleCommentQueryUseCase,
) {
    fun getArticleComment(articleId:Long): List<ArticleCommentCompositionDto> {
        val comments = articleCommentQueryUseCase.getCommentsByArticleId(articleId)

        if(comments.isEmpty()) return emptyList()

        val userUuids = comments.map { it.authorId }.distinct()
        val userMap = userQueryUseCase.getUsers(userUuids)

        val commentCompositionMap = comments.associate { dto ->
            val authorDto = userMap[dto.authorId] ?: UserInfoDto.fallback()
            dto.id to ArticleCommentCompositionDto.of(authorDto, dto)
        }

        val rootComments = mutableListOf<ArticleCommentCompositionDto>()
        comments.forEach { dto ->
            val currentComposition = commentCompositionMap[dto.id]!!

            if (dto.parentId == null) {
                rootComments.add(currentComposition)
            } else {
                val parentComposition = commentCompositionMap[dto.parentId]
                parentComposition?.children?.add(currentComposition)
            }
        }
        return rootComments
    }
}