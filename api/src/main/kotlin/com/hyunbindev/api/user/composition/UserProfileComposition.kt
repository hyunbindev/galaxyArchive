package com.hyunbindev.api.user.composition

import com.hyunbindev.api.user.data.UserProfileDto
import com.hyunbindev.article.article.port.inbound.ArticleStatsQueryUseCase
import com.hyunbindev.user.port.inbound.UserProfileQueryUseCase
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserProfileComposition(
    private val userProfileQueryUseCase: UserProfileQueryUseCase,
    private val articleStatsQueryUseCase: ArticleStatsQueryUseCase
) {
    fun getUserProfile(userId: UUID): UserProfileDto {
        val userProfile = userProfileQueryUseCase.getUserProfile(userId)
        val articleCount = articleStatsQueryUseCase.getArticleCountByAuthorId(userId)

        return with(userProfile) {
            UserProfileDto(
                userInfo = userInfo,
                bio = bio,
                articleCount = articleCount
            )
        }
    }
}