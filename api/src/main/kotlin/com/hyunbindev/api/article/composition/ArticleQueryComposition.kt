package com.hyunbindev.api.article.composition

import com.hyunbindev.api.article.data.ArticleCompositionResponse
import com.hyunbindev.article.article.port.inbound.ArticleQueryUseCase
import com.hyunbindev.article.article.data.ArticleDto
import com.hyunbindev.user.data.UserProfileDto
import com.hyunbindev.user.port.inbound.UserProfileQueryUseCase
import org.springframework.stereotype.Service

@Service
class ArticleQueryComposition(
    private val articleQueryUseCase: ArticleQueryUseCase,
    private val userProfileQueryUseCase: UserProfileQueryUseCase
) {
    fun getArticle(articleId: Long):ArticleCompositionResponse {
        val articleDto: ArticleDto.Response = articleQueryUseCase.getArticle(articleId)
        val authorDto: UserProfileDto = userProfileQueryUseCase.getUserProfile(articleDto.authorId)

        return ArticleCompositionResponse.of(articleDto,authorDto)
    }
}