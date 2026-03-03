package com.hyunbindev.user.application.signup

import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.entity.UserEntity
import com.hyunbindev.user.repository.UserRepository

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UserSignupService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun signup(userInfoDto: UserInfoDto) {
        val userEntity: UserEntity = UserEntity.from(userInfoDto)
        userRepository.save(userEntity)
    }
}