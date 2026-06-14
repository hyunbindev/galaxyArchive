package com.hyunbindev.user.data

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.domain.UserEntity
import com.hyunbindev.user.domain.UserProfileEntity
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
        fun from(userProfile: UserProfileEntity): UserInfoDto{
            return with(userProfile){
                UserInfoDto(
                    id = user.id,
                    nickName = nickName?:userProfile.user.nickName,
                    oAuth2Provider = user.oAuth2Provider,
                    email = user.email,
                    profileImageUrl = user.profileImageUrl,
                )
            }
        }

        fun from(user:UserEntity): UserInfoDto{
            return with(user){
                UserInfoDto(
                    id = id,
                    nickName = nickName,
                    oAuth2Provider = oAuth2Provider,
                    email = email,
                    profileImageUrl = profileImageUrl,
                )
            }
        }

        fun fallback():UserInfoDto{
            return UserInfoDto()
        }
    }
}