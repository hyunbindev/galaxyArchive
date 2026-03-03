package com.hyunbindev.user.exception.constant

import com.hyunbindev.common.exception.constant.ExceptionCode
import org.springframework.http.HttpStatus

enum class UserExceptionCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ExceptionCode{
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 사용자입니다."),
    USER_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"U002","내부 서버 오류")
}