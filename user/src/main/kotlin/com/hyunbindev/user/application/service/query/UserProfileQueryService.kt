package com.hyunbindev.user.application.service.query

import com.hyunbindev.user.adapter.outbound.UserProfileRepository
import com.hyunbindev.user.data.UserProfileDto
import com.hyunbindev.user.data.UserProfileEditInfoDto
import com.hyunbindev.user.domain.UserProfileEntity
import com.hyunbindev.user.global.exception.UserException
import com.hyunbindev.user.global.exception.constant.UserExceptionCode
import com.hyunbindev.user.port.usecase.inbound.UserProfileQueryUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly=true)
internal class UserProfileQueryService(
    private val userProfileRepository: UserProfileRepository
): UserProfileQueryUseCase {

    override fun getUserProfile(userId: UUID):UserProfileDto{
        val userProfile = userProfileRepository.findByUserId(userId)?:
            throw UserException(UserExceptionCode.USER_NOT_FOUND);
        return UserProfileDto.from(userProfile)
    }

    override fun getUserProfileEditInfo(userId: UUID): UserProfileEditInfoDto {
        val userProfile = userProfileRepository.findByUserId(userId)?:
            throw UserException(UserExceptionCode.USER_NOT_FOUND);
        return UserProfileEditInfoDto.from(userProfile)
    }

    override fun getUserProfiles(userIds: List<UUID>): Map<UUID,UserProfileDto> {
        val userProfiles:List<UserProfileEntity> = userProfileRepository.findByUserIds(userIds)
        return userProfiles
            .map { UserProfileDto.from(it) }
            .associateBy { it.userId as UUID }
    }
}