package com.hyunbindev.user.adapter.`in`.query

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.port.`in`.UserQueryUseCase
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.global.exception.UserException
import com.hyunbindev.user.global.exception.constant.UserExceptionCode
import com.hyunbindev.user.adapter.out.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
internal class UserQueryService(
    private val userRepository: UserRepository
): UserQueryUseCase {

    override fun isUser(provider: OAuth2Provider, providerId: String): Boolean {
        return userRepository.existsByOAuth2ProviderAndProviderId(provider, providerId)
    }

    override fun getUser(provider: OAuth2Provider,providerId: String): UserInfoDto {
        val user =  userRepository.findByOAuth2ProviderAndProviderId(provider,providerId)
            ?: throw UserException(UserExceptionCode.USER_NOT_FOUND)
        return UserInfoDto.from(user)
    }

    override fun getUser(userUuid: UUID): UserInfoDto {
        val user = userRepository.findUserById(userUuid)
            ?: throw UserException(UserExceptionCode.USER_NOT_FOUND)
        return UserInfoDto.from(user)
    }
}