package com.hyunbindev.user.application.service.command.signup

import com.hyunbindev.user.adapter.outbound.UserProfileRepository
import com.hyunbindev.user.port.usecase.inbound.UserSignupUseCase
import com.hyunbindev.user.data.UserInfoDto
import com.hyunbindev.user.domain.UserEntity
import com.hyunbindev.user.adapter.outbound.UserRepository
import com.hyunbindev.user.domain.UserProfileEntity

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class UserSignupService(
    private val userRepository: UserRepository,
    private val userProfileRepository: UserProfileRepository,
): UserSignupUseCase {
    @Transactional
    override fun signup(userInfoDto: UserInfoDto): UserInfoDto {
        val userEntity: UserEntity = UserEntity.from(userInfoDto)
        val savedUserEntity = userRepository.saveAndFlush(userEntity)

        val defaultUserProfileEntity: UserProfileEntity = UserProfileEntity.from(savedUserEntity)

        userProfileRepository.save(defaultUserProfileEntity)

        return UserInfoDto.from(userEntity)
    }
}