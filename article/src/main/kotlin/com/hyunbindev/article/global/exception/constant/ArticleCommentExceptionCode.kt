package com.hyunbindev.article.global.exception.constant

import com.hyunbindev.common.exception.constant.ExceptionCode
import org.springframework.http.HttpStatus

enum class ArticleCommentExceptionCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ExceptionCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_001", "존재하지 않는 덧글 입니다."),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMENT_002", "접근할 수 없는 덧글 입니다."),
    COMMENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMENT_003", "접근 권한이 없는 덧글 입니다."),
    COMMENT_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMENT_004","내부 서버 오류"),
    COMMENT_BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMENT_005","잘못된 덧글 요청입니다."),
}