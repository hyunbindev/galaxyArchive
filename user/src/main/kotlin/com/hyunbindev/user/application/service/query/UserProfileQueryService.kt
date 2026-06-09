package com.hyunbindev.user.application.service.query

import com.hyunbindev.user.adapter.outbound.UserProfileRepository
import com.hyunbindev.user.data.UserProfileDto
import com.hyunbindev.user.global.exception.UserException
import com.hyunbindev.user.global.exception.constant.UserExceptionCode
import com.hyunbindev.user.port.inbound.UserProfileQueryUseCase
import org.springframework.stereotype.Service
import java.util.UUID

@Service
internal class UserProfileQueryService(
    private val userProfileRepository: UserProfileRepository
): UserProfileQueryUseCase {
    override fun getUserProfile(userId: UUID):UserProfileDto{
        val userProfile = userProfileRepository.findByUserId(userId)?:
            throw UserException(UserExceptionCode.USER_NOT_FOUND);
        return UserProfileDto.from(userProfile)
    }
}