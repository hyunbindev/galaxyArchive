package com.hyunbindev.api.article.composition

import com.hyunbindev.api.article.data.ArticleCommentCompositionDto
import com.hyunbindev.article.comment.port.inbound.ArticleCommentQueryUseCase
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.data.UserProfileDto
import com.hyunbindev.user.port.inbound.UserProfileQueryUseCase
import com.hyunbindev.user.port.inbound.UserQueryUseCase
import org.springframework.stereotype.Service


@Service
class ArticleCommentComposition(
    private val userQueryUseCase: UserQueryUseCase,
    private val userProfileQueryUseCase: UserProfileQueryUseCase,
    private val articleCommentQueryUseCase: ArticleCommentQueryUseCase,
) {
    //TODO-삭제 및 답글 필터링 도메인 로직은 article module 안으로 이관
    fun getArticleComment(articleId:Long): List<ArticleCommentCompositionDto> {
        val comments = articleCommentQueryUseCase.getCommentsByArticleId(articleId)

        if(comments.isEmpty()) return emptyList()

        //author 요청시 삭제된 덧글은 제외 및 중복 제거
        val userUuids = comments
            .filter { !it.isDeleted }
            .map { it.authorId }
            .distinct()

        val userProfileMap = userProfileQueryUseCase.getUserProfiles(userUuids)
        // request author info

        // comment author info mapping
        val commentCompositionMap = comments.associate { comment ->

            val authorDto: UserProfileDto = if(comment.isDeleted) {
                UserProfileDto.fallback()
            }else {
                userProfileMap [comment.authorId] ?: UserProfileDto.fallback()
            }

            comment.id to ArticleCommentCompositionDto.of(authorDto, comment)
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