package com.hyunbindev.user.port.inbound

import java.util.UUID

interface UserProfileUpdateUseCase {
    fun updateUserProfile(userId:UUID, bio:String?, nickName:String?)

}