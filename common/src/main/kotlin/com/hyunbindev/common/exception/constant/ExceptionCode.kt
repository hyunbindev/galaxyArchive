package com.hyunbindev.common.exception.constant

import org.springframework.http.HttpStatus

/**
 * 서비스 내의 모든 에러 코드 Enum이 구현해야 하는 공통 인터페이스입니다.
 * * 이 인터페이스를 구현함으로써 [CommonException]에서 에러 정보를 일관되게 참조하고,
 * GlobalExceptionHandler에서 표준화된 에러 응답(ErrorResponse)을 생성할 수 있습니다.
 */
interface ExceptionCode {
    /**
     * 클라이언트에게 반환할 HTTP 상태 코드
     * (예: [HttpStatus.NOT_FOUND], [HttpStatus.BAD_REQUEST])
     */
    val status: HttpStatus

    /**
     * 서비스에서 정의한 고유 에러 코드
     * (예: "U001", "AUTH002" 등)
     */
    val code: String

    /**
     * 예외 발생 시 반환할 기본 에러 메시지
     * [CommonException]에서 별도의 메시지를 인자로 받지 않을 경우 이 값이 사용됩니다.
     */
    val message: String
}