package com.hyunbindev.article.global.exception

import com.hyunbindev.common.exception.CommonException
import com.hyunbindev.common.exception.constant.ExceptionCode

class ArticleException(
    exceptionCode: ExceptionCode,
    message: String? = null,
    cause: Throwable? = null) : CommonException(exceptionCode, message, cause)