package com.hyunbindev.user.repository

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository :JpaRepository<UserEntity, UUID> {
    fun existsByOAuth2ProviderAndProviderId(oAuth2Provider: OAuth2Provider, providerId:String): Boolean
    fun findByOAuth2ProviderAndProviderId(oAuth2Provider: OAuth2Provider, providerId: String): UserEntity?
}