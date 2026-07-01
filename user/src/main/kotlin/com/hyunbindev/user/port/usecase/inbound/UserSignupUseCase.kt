package com.hyunbindev.user.port.usecase.inbound

import com.hyunbindev.user.data.UserInfoDto

interface UserSignupUseCase {
    fun signup(userInfoDto: UserInfoDto):UserInfoDto
}