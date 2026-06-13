package com.hyunbindev.api.article.data

import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.data.UserProfileDto
import java.time.LocalDateTime
import java.util.UUID

data class ArticleCompositionResponse(
    val id:Long?,
    val title:String,
    val text:String,
    val author:AuthorDto,
    val createdAt: LocalDateTime,
){
    companion object{
        fun of(articleDto: ArticleDto.Response, userProfileDto: UserProfileDto ): ArticleCompositionResponse{
            return ArticleCompositionResponse(
                id = articleDto.id,
                title = articleDto.title,
                text = articleDto.text,
                author = AuthorDto.from(userProfileDto),
                createdAt = articleDto.createdAt,
            )
        }
    }
}

data class AuthorDto(
    val id: UUID?,
    val nickName:String,
    val profileImageUrl:String?,
    val bio:String
){
    companion object{
        fun from(userProfileDto: UserProfileDto): AuthorDto{
            return AuthorDto(
                id=userProfileDto.userId,
                nickName=userProfileDto.nickName,
                profileImageUrl = userProfileDto.userProfileImageUrl,
                bio = userProfileDto.bio
            )
        }
    }
}