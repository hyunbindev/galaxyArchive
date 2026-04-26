package com.hyunbindev.api.user.controller

import com.hyunbindev.common.auth.LoginUserId
import com.hyunbindev.user.application.port.UserQueryUseCase
import com.hyunbindev.user.data.UserInfoDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userQueryUseCase: UserQueryUseCase
) {
    @GetMapping("/me")
    fun getUserSelf(@LoginUserId userId: UUID): ResponseEntity<UserInfoDto> {
        return ResponseEntity.ok(userQueryUseCase.getUser(userId))
    }

    @GetMapping("/verify")
    fun verifyUser(@LoginUserId userId: UUID): ResponseEntity<Void> {

        return ResponseEntity.ok().build()
    }
}