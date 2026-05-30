package com.hyunbindev.user.application.service.command.update

import com.hyunbindev.user.port.inbound.UserUpdateUseCase
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.domain.UserEntity
import com.hyunbindev.user.global.exception.UserException
import com.hyunbindev.user.global.exception.constant.UserExceptionCode
import com.hyunbindev.user.adapter.outbound.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserUpdateService(
    private val userRepository: UserRepository,
): UserUpdateUseCase {
    @Transactional
    override fun update(userInfoDto: UserInfoDto): UserInfoDto {
        val provider = requireNotNull(userInfoDto.oAuth2Provider) { throw UserException(UserExceptionCode.USER_INTERNAL_ERROR) }
        val providerId = requireNotNull(userInfoDto.providerId) { throw UserException(UserExceptionCode.USER_INTERNAL_ERROR) }

        val user:UserEntity = userRepository.findByOAuth2ProviderAndProviderId(provider, providerId)
            ?:throw UserException(UserExceptionCode.USER_NOT_FOUND);

        user.lastLoginAt = LocalDateTime.now()

        userInfoDto.nickName?.let { user.nickName = it }
        userInfoDto.profileImageUrl?.let { user.profileImageUrl = it }
        userInfoDto.email?.let { user.email = it }

        return UserInfoDto.from(user)
    }
}