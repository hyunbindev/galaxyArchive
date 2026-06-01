package com.hyunbindev.api.article.data

import com.hyunbindev.article.comment.data.ArticleCommentDto
import com.hyunbindev.user.data.UserInfoDto
import java.time.LocalDateTime
import kotlin.collections.mutableListOf

data class ArticleCreateRequestDto(
    val text: String,
)

data class ArticleCommentCompositionDto(
    val id: Long,
    val author: UserInfoDto,
    val createdAt: LocalDateTime,
    val text: String
){
    val children: MutableList<ArticleCommentCompositionDto> = mutableListOf()

    companion object{
        fun of(author:UserInfoDto, articleCommentDto: ArticleCommentDto): ArticleCommentCompositionDto {
            return ArticleCommentCompositionDto(
                id = articleCommentDto.id,
                author = author,
                createdAt = articleCommentDto.createdAt,
                text = articleCommentDto.text,
            )
        }
    }
}