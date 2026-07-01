package com.hyunbindev.api.user.composition

import com.hyunbindev.api.user.data.UserProfileCompositionResponse
import com.hyunbindev.article.article.port.usecase.inbound.ArticleStatsQueryUseCase
import com.hyunbindev.user.port.usecase.inbound.UserProfileQueryUseCase
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserProfileComposition(
    private val userProfileQueryUseCase: UserProfileQueryUseCase,
    private val articleStatsQueryUseCase: ArticleStatsQueryUseCase
) {
    fun getUserProfile(userId: UUID): UserProfileCompositionResponse {
        val userProfile = userProfileQueryUseCase.getUserProfile(userId)
        val articleCount = articleStatsQueryUseCase.getArticleCountByAuthorId(userId)

        return UserProfileCompositionResponse(
            userId = userProfile.userId,
            nickName = userProfile.nickName,
            userProfileImageUrl = userProfile.userProfileImageUrl,
            email = userProfile.email,
            bio = userProfile.bio?:"",
            articleCount = articleCount
        )
    }
}