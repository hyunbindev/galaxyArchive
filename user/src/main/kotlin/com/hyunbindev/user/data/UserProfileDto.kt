package com.hyunbindev.user.data

import com.hyunbindev.user.domain.UserProfileEntity
import java.util.UUID


data class UserProfileDto(
    val userId:UUID,
    val nickName:String,
    val userProfileImageUrl:String?=null,
    val email:String?=null,
    val bio: String,
) {
    companion object {
        fun from(userProfileEntity: UserProfileEntity ): UserProfileDto {
            return with(userProfileEntity){
                UserProfileDto(
                    userId = userId,
                    nickName = nickName?:user.nickName?:"UNKNOWN",
                    userProfileImageUrl = user.profileImageUrl,
                    bio = bio,
                )
            }
        }
    }
}