package com.hyunbindev.auth

import com.hyunbindev.auth.oauth2.model.OAuth2UserPrincipal
import com.hyunbindev.common.auth.UserProvider
import com.sun.security.auth.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import java.util.UUID

class OAuth2UserProvider : UserProvider {
    override fun getUserId(): UUID {
        val principal = SecurityContextHolder.getContext().authentication.principal as OAuth2UserPrincipal

        //TODO - need to AUTH exception implementation
        return principal.userId ?: throw RuntimeException("No user found with id $principal")
    }
}