package com.hyunbindev.auth.oauth2.model

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.data.UserInfoDto
import java.util.UUID

interface OAuth2UserPrincipal {
    val userId: UUID?
    val providerId: String
    val provider: OAuth2Provider
    val nickname: String
    val profileImageUrl: String?
    val email: String?

    fun toUserDto(): UserInfoDto
}