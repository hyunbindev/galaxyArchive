package com.hyunbindev.article.exception.constant

import com.hyunbindev.common.exception.constant.ExceptionCode
import org.springframework.http.HttpStatus

enum class ArticleImageExceptionCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
):ExceptionCode {
    ARTICLEIMAGE_TEMPTRASACTION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "I001", "이미지 업로드에 실패했습니다."),
    ARTICLEIMAGE_INFO_LOST(HttpStatus.INTERNAL_SERVER_ERROR,"I002","이미지 업로드데이터가 유실되었습니다.")
}