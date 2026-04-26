package com.hyunbindev.auth.application.port

import java.util.UUID

interface UserProviderUseCase {
    fun getLoginUserId(): UUID
}