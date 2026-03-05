package com.hyunbindev.auth

import com.hyunbindev.auth.exception.AuthException
import com.hyunbindev.auth.exception.constant.AuthExceptionCode
import com.hyunbindev.auth.oauth2.model.OAuth2UserPrincipal
import com.hyunbindev.common.auth.UserProvider
import com.sun.security.auth.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import java.util.UUID

class OAuth2UserProvider : UserProvider {
    override fun getLoginUserId(): UUID {
        val authentication = SecurityContextHolder.getContext().authentication?:throw AuthException(AuthExceptionCode.USER_UNAUTHORIZED)
        val principal = authentication.principal as OAuth2UserPrincipal


        return principal.userId ?: throw AuthException(AuthExceptionCode.USER_FORBIDDEN)
    }
}