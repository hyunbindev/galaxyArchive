package com.hyunbindev.user.application

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.application.signup.UserSignupService
import com.hyunbindev.user.application.update.UserUpdateService
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
internal class UserServiceImpl(
    private val userSignupService: UserSignupService,
    private val userUpdateService: UserUpdateService,

    private val userRepository: UserRepository,
) : UserService {
    override fun signup(userInfoDto: UserInfoDto) {
        userSignupService.signup(userInfoDto)
    }

    override fun update(userInfoDto: UserInfoDto) {
        userUpdateService.update(userInfoDto)
    }

    override fun isUser(oAuth2Provider: OAuth2Provider, providerId:String):Boolean {
        return userRepository.existsByOAuth2ProviderAndProviderId(oAuth2Provider,providerId)
    }
}