package com.hyunbindev.user.domain

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.user.data.UserInfoDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import jakarta.persistence.Table
import jakarta.persistence.Transient
import jakarta.persistence.UniqueConstraint
import jakarta.transaction.Transactional
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(
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
): Persistable<UUID> {
    @Id
    @Column(name="id",columnDefinition = "UUID", nullable = false)
    val userId: UUID = UUID.randomUUID()

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

    @Transient
    var isNewRecord:Boolean = false

    override fun getId(): UUID? = this.userId

    override fun isNew(): Boolean = isNewRecord

    @PostPersist
    @PostLoad
    private fun markNotNew(){
        this.isNewRecord = false
    }

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