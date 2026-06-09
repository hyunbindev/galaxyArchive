package com.hyunbindev.api.user.data

import com.hyunbindev.user.data.UserInfoDto

data class UserProfileDto(
    val userInfo: UserInfoDto,
    val bio: String,
    val articleCount: Int,
    //TODO-게시글 클러스터 개수 연산 구현 필요
    val clusterCount: Int = 0,
    //TODO-엔지 갯수 연산 구현 필요
    val connectionCount: Int = 0,
)