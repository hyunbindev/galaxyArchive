package com.hyunbindev.api.article.composition

import com.hyunbindev.api.article.data.ArticleCompositionDto
import com.hyunbindev.article.article.port.inbound.ArticleQueryUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.user.port.inbound.UserQueryUseCase
import com.hyunbindev.user.data.UserInfoDto
import org.springframework.stereotype.Service

@Service
class ArticleQueryComposition(
    private val userQueryUseCase: UserQueryUseCase,
    private val articleQueryUseCase: ArticleQueryUseCase
) {
    fun getArticle(articleId: Long):ArticleCompositionDto {
        val articleDto: ArticleDto.Response = articleQueryUseCase.getArticle(articleId)
        val authorDto: UserInfoDto = userQueryUseCase.getUser(articleDto.authorId)

        return ArticleCompositionDto.of(articleDto,authorDto)
    }
}