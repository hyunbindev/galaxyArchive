package com.hyunbindev.user.application

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.data.UserInfoDto

interface UserService {
    fun signup(userInfoDto: UserInfoDto)
    fun update(userInfoDto: UserInfoDto)
    fun isUser(oAuth2Provider: OAuth2Provider, providerId:String):Boolean
}