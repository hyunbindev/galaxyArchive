package com.hyunbindev.api.article.data

import com.hyunbindev.article.data.dto.ArticleDto
import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.data.UserInfoDto
import java.time.LocalDateTime
import java.util.UUID

data class ArticleCompositionDto(
    val id:Long?,
    val title:String,
    val text:String,
    val author:AuthorDto,
    val createdAt: LocalDateTime,
){
    companion object{
        fun of(articleDto: ArticleDto.Response, userInfoDto: UserInfoDto ): ArticleCompositionDto{
            return ArticleCompositionDto(
                id = articleDto.id,
                title = articleDto.title,
                text = articleDto.text,
                author = AuthorDto.from(userInfoDto),
                createdAt = articleDto.createdAt,
            )
        }
    }
}

data class AuthorDto(
    val id: UUID?,
    val nickName:String?,
    val oAuth2Provider: OAuth2Provider?,
    val profileImageUrl:String?,
){
    companion object{
        fun from(userInfoDto: UserInfoDto): AuthorDto{
            return AuthorDto(
                id=userInfoDto.id,
                nickName=userInfoDto.nickName,
                oAuth2Provider = userInfoDto.oAuth2Provider,
                profileImageUrl = userInfoDto.profileImageUrl,
            )
        }
    }
}