package com.hyunbindev.auth.oauth2.service

import com.hyunbindev.auth.oauth2.model.OAuth2UserPrincipalImpl
import com.hyunbindev.user.port.`in`.UserQueryUseCase
import com.hyunbindev.user.port.`in`.UserSignupUseCase
import com.hyunbindev.user.port.`in`.UserUpdateUseCase
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserService(
    private val userSignupUseCase: UserSignupUseCase,
    private val userUpdateUseCase: UserUpdateUseCase,
    private val userQueryUseCase: UserQueryUseCase,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User? {
        val oAuth2User = super.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId

        val userPrincipal = OAuth2UserPrincipalImpl.of(registrationId, oAuth2User)

        val isUser = userQueryUseCase.isUser(userPrincipal.provider,userPrincipal.providerId)

        if(isUser){
            userPrincipal.userId = userUpdateUseCase.update(userPrincipal.toUserDto()).id
        }else{
            userPrincipal.userId = userSignupUseCase.signup(userPrincipal.toUserDto()).id
        }

        return userPrincipal;
    }
}