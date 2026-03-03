package com.hyunbindev.user.application.update

import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.entity.UserEntity
import com.hyunbindev.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserUpdateService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun update(userInfoDto: UserInfoDto) {
        val provider = requireNotNull(userInfoDto.oAuth2Provider) { "Provider must not be null" }
        val providerId = requireNotNull(userInfoDto.providerId) { "Provider ID must not be null" }

        val user:UserEntity = userRepository.findByOAuth2ProviderAndProviderId(provider, providerId)
            ?:throw RuntimeException("not user");

        user.lastLoginAt = LocalDateTime.now()

        userInfoDto.nickName?.let { user.nickName = it }
        userInfoDto.profileImageUrl?.let { user.profileImageUrl = it }
        userInfoDto.email?.let { user.email = it }
    }
}