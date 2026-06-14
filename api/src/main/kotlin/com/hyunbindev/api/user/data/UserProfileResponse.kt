package com.hyunbindev.api.user.data

import java.util.UUID

data class UserProfileCompositionResponse(
    val userId: UUID,
    val nickName:String,
    val userProfileImageUrl:String?=null,
    val email:String?=null,
    val bio: String,
    val articleCount: Int,
    //TODO-게시글 클러스터 개수 연산 구현 필요
    val clusterCount: Int = 0,
    //TODO-엔지 갯수 연산 구현 필요
    val connectionCount: Int = 0,
)

data class UserProfileEditResponse(
    val defaultNickName:String?,
    val nickName:String?,
    val bio:String?,
)