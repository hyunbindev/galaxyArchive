package com.hyunbindev.article.global.exception.constant

import com.hyunbindev.common.exception.constant.ExceptionCode
import org.springframework.http.HttpStatus

enum class ArticleExceptionCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ExceptionCode {
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "존재하지 않는 게시글 입니다."),
    ARTICLE_FORBIDDEN(HttpStatus.FORBIDDEN, "A002", "접근할 수 없는 게시글 입니다."),
    ARTICLE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A003", "접근 권한이 없는 게시글 입니다."),
    ARTICLE_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"A004","내부 서버 오류")
}