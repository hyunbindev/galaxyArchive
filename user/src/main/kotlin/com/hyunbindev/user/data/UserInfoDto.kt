package com.hyunbindev.user.data

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.domain.UserEntity
import org.springframework.boot.autoconfigure.security.SecurityProperties
import java.util.UUID

data class UserInfoDto(
    var id: UUID? = null,
    var nickName: String? = null,
    var providerId: String? = null,
    var oAuth2Provider: OAuth2Provider? = null,
    var email: String? = null,
    var profileImageUrl: String? = null,
){
    companion object{
        fun from(entity: UserEntity): UserInfoDto{
            return UserInfoDto(
                id = entity.id,
                nickName = entity.nickName,
                oAuth2Provider = entity.oAuth2Provider,
                email = entity.email,
                profileImageUrl = entity.profileImageUrl,
            )
        }
    }
}