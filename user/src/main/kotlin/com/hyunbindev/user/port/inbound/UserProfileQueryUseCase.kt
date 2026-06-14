package com.hyunbindev.user.port.inbound

import com.hyunbindev.user.data.UserProfileDto
import com.hyunbindev.user.data.UserProfileEditInfoDto
import java.util.UUID

interface UserProfileQueryUseCase {
    fun getUserProfile(userId: UUID):UserProfileDto

    fun getUserProfileEditInfo(userId:UUID):UserProfileEditInfoDto

    fun getUserProfiles(userIds:List<UUID>):Map<UUID,UserProfileDto>

}