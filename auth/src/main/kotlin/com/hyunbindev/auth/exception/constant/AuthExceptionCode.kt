package com.hyunbindev.auth.exception.constant

import com.hyunbindev.common.exception.constant.ExceptionCode
import org.springframework.http.HttpStatus

enum class AuthExceptionCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ExceptionCode{
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_001", "인증되지 않은 사용자입니다."),
    USER_FORBIDDEN(HttpStatus.FORBIDDEN,"AUTH_002","해당 리소스에 대한 권한이 없습니다.")
}