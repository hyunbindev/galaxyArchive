package com.hyunbindev.user.port.inbound

import com.hyunbindev.user.data.UserProfileDto
import java.util.UUID

interface UserProfileQueryUseCase {
    fun getUserProfile(userId: UUID):UserProfileDto
}