package com.hyunbindev.common.auth

import com.sun.security.auth.UserPrincipal
import java.util.UUID

interface UserProvider {
    fun getUserId(): UUID
}