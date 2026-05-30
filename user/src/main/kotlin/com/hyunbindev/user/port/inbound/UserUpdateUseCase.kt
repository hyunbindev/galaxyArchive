package com.hyunbindev.user.port.inbound

import com.hyunbindev.user.data.UserInfoDto

interface UserUpdateUseCase {
    fun update(userInfoDto: UserInfoDto):UserInfoDto
}