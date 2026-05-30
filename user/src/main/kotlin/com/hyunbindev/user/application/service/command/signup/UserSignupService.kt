package com.hyunbindev.user.application.service.command.signup

import com.hyunbindev.user.port.inbound.UserSignupUseCase
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.domain.UserEntity
import com.hyunbindev.user.adapter.outbound.UserRepository

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