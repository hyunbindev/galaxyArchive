package com.hyunbindev.user.entity

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.data.UserInfoDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(
    name = "user_entity",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_provider_id", columnNames = ["o_auth2provider", "provider_id"])
    ]
)
class UserEntity(
    @Column(nullable = false)
    var nickName: String?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    val oAuth2Provider: OAuth2Provider? = null,

    @Column(nullable = true, unique = true)
    val providerId: String? = null,

    @Column(nullable = true)
    var profileImageUrl: String? = null,

    @Column(nullable = true)
    var email: String? = null
) {
    @Id
    @Column(columnDefinition = "UUID", nullable = false)
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    var deleted: Boolean = false

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var lastLoginAt: LocalDateTime? = LocalDateTime.now()

    companion object{
        fun from(userInfoDto: UserInfoDto):UserEntity{
            return UserEntity(
                providerId = userInfoDto.providerId,
                nickName = userInfoDto.nickName,
                oAuth2Provider = userInfoDto.oAuth2Provider,
                profileImageUrl = userInfoDto.profileImageUrl,
                email = userInfoDto.email
            )
        }
    }
}