package com.hyunbindev.user.data

import com.hyunbindev.user.domain.UserProfileEntity

data class UserProfileEditInfoDto(
    val defaultNickName: String?,
    val nickName: String?,
    val bio: String?,
){
    companion object {
        fun from(userProfile: UserProfileEntity):UserProfileEditInfoDto{
            return UserProfileEditInfoDto(
                defaultNickName = userProfile.user.nickName,
                nickName = userProfile.nickName,
                bio = userProfile.bio,
            )
        }
    }
}