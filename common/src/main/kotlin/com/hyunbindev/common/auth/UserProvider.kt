package com.hyunbindev.common.auth

import com.sun.security.auth.UserPrincipal

interface UserProvider {
    fun getUserPrincipal(): UserPrincipal
}