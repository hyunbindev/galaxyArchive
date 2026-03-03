package com.hyunbindev.auth.configuration.oauth2.service

import com.hyunbindev.auth.configuration.oauth2.model.OAuth2UserPrincipalImpl
import com.hyunbindev.user.application.UserService
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserService(
    private val userService: UserService,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User? {
        val oAuth2User = super.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId

        val userPrincipal = OAuth2UserPrincipalImpl.of(registrationId, oAuth2User)

        val isUser = userService.isUser(userPrincipal.provider,userPrincipal.providerId)

        if(isUser){
            userService.signup(userPrincipal.toUserDto())
        }else{
            userService.update(userPrincipal.toUserDto())
        }


        return userPrincipal;
    }
}