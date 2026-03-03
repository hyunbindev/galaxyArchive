package com.hyunbindev.user.exception

import com.hyunbindev.common.exception.CommonException
import com.hyunbindev.common.exception.constant.ExceptionCode

class UserException(
    exceptionCode: ExceptionCode,
    message: String? = null,
    cause: Throwable? = null) :
    CommonException(exceptionCode, message, cause) {
}