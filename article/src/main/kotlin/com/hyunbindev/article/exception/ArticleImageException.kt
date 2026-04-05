package com.hyunbindev.article.exception

import com.hyunbindev.common.exception.CommonException
import com.hyunbindev.common.exception.constant.ExceptionCode

class ArticleImageException(
    exceptionCode: ExceptionCode,
    message: String? = null,
    cause: Throwable? = null
) : CommonException(exceptionCode, message, cause)