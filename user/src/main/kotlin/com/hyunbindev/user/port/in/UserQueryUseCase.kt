package com.hyunbindev.user.port.`in`

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.data.UserInfoDto
import java.util.UUID

interface UserQueryUseCase {
    fun isUser(provider: OAuth2Provider,providerId:String):Boolean
    fun getUser(provider: OAuth2Provider,providerId:String):UserInfoDto
    fun getUser(userUuid: UUID):UserInfoDto
}