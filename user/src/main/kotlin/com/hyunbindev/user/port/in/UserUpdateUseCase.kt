package com.hyunbindev.user.port.`in`

import com.hyunbindev.user.data.UserInfoDto

interface UserUpdateUseCase {
    fun update(userInfoDto: UserInfoDto):UserInfoDto
}