package com.hyunbindev.api.article.data

import com.hyunbindev.article.comment.data.ArticleCommentDto
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.data.UserProfileDto
import java.time.LocalDateTime
import kotlin.collections.mutableListOf

data class ArticleCreateRequestDto(
    val text: String,
)

data class ArticleCommentCompositionDto(
    val id: Long,
    val author: UserProfileDto,
    val createdAt: LocalDateTime,
    val text: String,
    val isDeleted: Boolean
){
    val children: MutableList<ArticleCommentCompositionDto> = mutableListOf()

    companion object{
        fun of(author:UserProfileDto, articleCommentDto: ArticleCommentDto): ArticleCommentCompositionDto {
            return ArticleCommentCompositionDto(
                id = articleCommentDto.id,
                author = author,
                createdAt = articleCommentDto.createdAt,
                text = if (articleCommentDto.isDeleted) "" else articleCommentDto.text,
                isDeleted = articleCommentDto.isDeleted
            )
        }
    }
}