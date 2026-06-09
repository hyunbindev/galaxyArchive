package com.hyunbindev.user.data

import com.hyunbindev.user.domain.UserProfileEntity


data class UserProfileDto(
    val userInfo: UserInfoDto,
    val bio: String,
) {
    companion object {
        fun from(userProfileEntity: UserProfileEntity ): UserProfileDto {
            return UserProfileDto(
                userInfo = UserInfoDto.from(userProfileEntity.user),
                bio = userProfileEntity.bio
            )
        }
    }
}