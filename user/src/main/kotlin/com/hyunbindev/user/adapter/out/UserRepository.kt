package com.hyunbindev.user.adapter.out

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<UserEntity, UUID> {
    fun existsByOAuth2ProviderAndProviderId(oAuth2Provider: OAuth2Provider, providerId:String): Boolean
    fun findByOAuth2ProviderAndProviderId(oAuth2Provider: OAuth2Provider, providerId: String): UserEntity?


    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    fun findUserById(@Param("id")uuid: UUID): UserEntity?
}