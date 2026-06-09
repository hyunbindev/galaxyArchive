package com.hyunbindev.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.springframework.data.domain.Persistable
import java.util.UUID

@Entity
class UserProfileEntity(
    @Id
    @Column(name="user_id")
    val userId: UUID,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity
) : Persistable<UUID> {

    var bio: String = ""

    @Transient
    private var isNewRecord: Boolean = true

    override fun getId(): UUID? = this.userId

    override fun isNew(): Boolean = this.isNewRecord

    @PostPersist
    @PostLoad
    private fun markNotNew(){
        this.isNewRecord = false
    }

    companion object {
        fun from (userEntity: UserEntity): UserProfileEntity {
            return UserProfileEntity(userEntity.userId, userEntity)
        }
    }
}