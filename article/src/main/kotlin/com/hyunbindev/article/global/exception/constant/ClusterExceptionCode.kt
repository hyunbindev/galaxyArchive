package com.hyunbindev.article.global.exception.constant

import com.hyunbindev.common.exception.constant.ExceptionCode
import org.springframework.http.HttpStatus

enum class ClusterExceptionCode (
    override val status: HttpStatus,
    override val code: String,
    override val message: String
    ): ExceptionCode {
    NO_USER_CLUSTER(HttpStatus.NOT_FOUND, "C001", "사용자 클러스터가 존재하지 않습니다.")
}