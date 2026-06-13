package com.hyunbindev.api.user.controller

import com.hyunbindev.api.user.composition.UserProfileComposition
import com.hyunbindev.api.user.data.UserProfileCompositionResponse
import com.hyunbindev.api.user.data.UserProfileUpdateRequest
import com.hyunbindev.common.auth.LoginUserId
import com.hyunbindev.user.port.inbound.UserProfileUpdateUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users/profiles")
class UserProfileController(
    private val userProfileComposition: UserProfileComposition,
    private val userProfileUpdateUseCase: UserProfileUpdateUseCase
) {
    @GetMapping("/{userId}")
    fun getUserProfile(@PathVariable userId: UUID): UserProfileCompositionResponse {
        return userProfileComposition.getUserProfile(userId)
    }

    @PatchMapping
    fun updateUserProfile(@LoginUserId userId:UUID, @RequestBody updateRequest: UserProfileUpdateRequest) {
        userProfileUpdateUseCase.updateUserProfile(userId,updateRequest.bio,updateRequest.nickName)
    }

}