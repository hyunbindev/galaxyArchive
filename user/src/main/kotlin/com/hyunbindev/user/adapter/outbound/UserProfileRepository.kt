package com.hyunbindev.user.adapter.outbound

import com.hyunbindev.user.domain.UserEntity
import com.hyunbindev.user.domain.UserProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserProfileRepository : JpaRepository<UserProfileEntity, UserEntity> {
    @Query(
        value = """
            SELECT p from UserProfileEntity p
            JOIN FETCH p.user
            WHERE p.userId = :userId
        """
    )
    fun findByUserId(userId:UUID):UserProfileEntity?


    @Query(
        value="""
            SELECT p FROM UserProfileEntity p
            JOIN FETCH p.user
            WHERE p.userId IN :userIds
        """
    )
    fun findByUserIds(userIds:List<UUID>):List<UserProfileEntity>
}