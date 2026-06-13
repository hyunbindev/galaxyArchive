package com.hyunbindev.user.port.inbound

import com.hyunbindev.user.data.UserProfileDto
import java.util.UUID

interface UserProfileUpdateUseCase {
    fun updateUserProfile(userId:UUID, bio:String?, nickName:String?)
}