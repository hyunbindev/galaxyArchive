package com.hyunbindev.user.adapter.outbound

import com.hyunbindev.user.domain.UserEntity
import com.hyunbindev.user.domain.UserProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserProfileRepository : JpaRepository<UserProfileEntity, UserEntity> {
}