package com.hyunbindev.user.port.usecase.inbound

import java.util.UUID

interface UserProfileUpdateUseCase {
    fun updateUserProfile(userId:UUID, bio:String?, nickName:String?)

}