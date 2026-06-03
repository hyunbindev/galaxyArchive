package com.hyunbindev.api.article.composition

import com.hyunbindev.api.article.data.ArticleCommentCompositionDto
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

        //author 요청시 삭제된 덧글은 제외 및 중복 제거
        val userUuids = comments
            .filter { !it.isDeleted }
            .map { it.authorId }
            .distinct()

        // request author info
        val userMap = userQueryUseCase.getUsers(userUuids)

        // comment author info mapping
        val commentCompositionMap = comments.associate { dto ->

            val authorDto: UserInfoDto = if(dto.isDeleted) {
                UserInfoDto.fallback()
            }else {
                userMap[dto.authorId] ?: UserInfoDto.fallback()
            }

            dto.id to ArticleCommentCompositionDto.of(authorDto, dto)
        }

        // recomment mapping
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

        // filtering no children comments and parent is deleted case
        val filteredRootComments = rootComments.filterNot { it.children.isEmpty() && it.isDeleted }

        return filteredRootComments
    }
}