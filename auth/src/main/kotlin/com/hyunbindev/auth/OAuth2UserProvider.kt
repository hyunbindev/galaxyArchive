package com.hyunbindev.auth

import com.hyunbindev.common.auth.UserProvider
import com.sun.security.auth.UserPrincipal

class OAuth2UserProvider : UserProvider {
    override fun getUserPrincipal(): UserPrincipal {
        TODO("Not yet implemented")
    }
}