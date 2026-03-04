package com.hyunbindev.user.application.update

import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.entity.UserEntity
import com.hyunbindev.user.exception.UserException
import com.hyunbindev.user.exception.constant.UserExceptionCode
import com.hyunbindev.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserUpdateService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun update(userInfoDto: UserInfoDto): UserInfoDto {
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