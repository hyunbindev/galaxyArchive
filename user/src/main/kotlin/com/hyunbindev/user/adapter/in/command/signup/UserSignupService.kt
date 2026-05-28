package com.hyunbindev.user.adapter.`in`.command.signup

import com.hyunbindev.user.port.`in`.UserSignupUseCase
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.domain.UserEntity
import com.hyunbindev.user.adapter.out.UserRepository

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UserSignupService(
    private val userRepository: UserRepository,
): UserSignupUseCase {
    @Transactional
    override fun signup(userInfoDto: UserInfoDto): UserInfoDto {
        val userEntity: UserEntity = UserEntity.from(userInfoDto)
        userRepository.save(userEntity)
        return UserInfoDto.from(userEntity)
    }
}