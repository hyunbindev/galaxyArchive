package com.hyunbindev.auth.exception

import com.hyunbindev.common.exception.CommonException
import com.hyunbindev.common.exception.constant.ExceptionCode

class AuthException(
    exceptionCode: ExceptionCode,
    message: String? = null,
    cause: Throwable? = null) : CommonException(exceptionCode, message, cause)