package com.hyunbindev.auth

import com.hyunbindev.auth.exception.AuthException
import com.hyunbindev.auth.exception.constant.AuthExceptionCode
import com.hyunbindev.auth.oauth2.model.OAuth2UserPrincipal
import com.hyunbindev.common.auth.UserProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
internal class OAuth2UserProvider : UserProvider {
    override fun getLoginUserId(): UUID {

        val authentication = SecurityContextHolder.getContext().authentication?:throw AuthException(AuthExceptionCode.USER_UNAUTHORIZED)
        val principal = authentication.principal as OAuth2UserPrincipal

        return principal.userId ?: throw AuthException(AuthExceptionCode.USER_FORBIDDEN)
    }
}