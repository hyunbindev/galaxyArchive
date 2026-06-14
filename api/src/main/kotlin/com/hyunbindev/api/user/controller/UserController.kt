package com.hyunbindev.api.user.controller

import com.hyunbindev.api.user.composition.UserProfileComposition
import com.hyunbindev.api.user.data.UserProfileCompositionResponse

import com.hyunbindev.article.article.data.ArticleSummaryPageDto
import com.hyunbindev.article.article.port.inbound.ArticleQueryUseCase
import com.hyunbindev.common.auth.LoginUserId
import com.hyunbindev.user.port.inbound.UserQueryUseCase
import com.hyunbindev.user.data.UserInfoDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userQueryUseCase: UserQueryUseCase,
    private val articleQueryUseCase: ArticleQueryUseCase,
) {
    @GetMapping("/me")
    fun getUserSelf(@LoginUserId userId: UUID): UserInfoDto {
        return userQueryUseCase.getUser(userId)
    }

    @GetMapping("/verify")
    fun verifyUser(@LoginUserId userId: UUID): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{userId}/articles")
    fun getArticleByUser(
        @PathVariable userId: UUID,
        @RequestParam(required = false) size: Int?,
        @RequestParam(required = false) lastArticleId: Long?
    ): ArticleSummaryPageDto {
        return articleQueryUseCase.getArticleSummaryPageByCursorAndAuthor(userId, lastArticleId, size ?: 10)
    }

}