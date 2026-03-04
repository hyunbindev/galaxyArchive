package com.hyunbindev.common.exception

import com.hyunbindev.common.exception.constant.ExceptionCode
import org.springframework.http.HttpStatus

/**
 * 서비스 전체에서 사용하는 공통 예외 추상 클래스입니다.
 * * 상속(Inheritance)을 통해 구체적인 예외를 정의하며,
 * [ExceptionCode]를 컴포지션으로 포함하여 에러 코드와 상태를 관리합니다.
 *
 * @property exceptionCode 에러 코드 인터페이스 (HttpStatus, 커스텀 코드, 기본 메시지 포함)
 * @property message 예외 발생 시 전달할 상세 메시지. null일 경우 [exceptionCode.message]를 기본값으로 사용합니다.
 * @property cause 근본 원인이 되는 예외 (Throwable)
 */
abstract class CommonException(
    val exceptionCode: ExceptionCode,
    override val message:String? = exceptionCode.message,
    cause: Throwable? = null,
) : RuntimeException(message?:exceptionCode.message, cause){

    fun getStatus(): HttpStatus = exceptionCode.status
}