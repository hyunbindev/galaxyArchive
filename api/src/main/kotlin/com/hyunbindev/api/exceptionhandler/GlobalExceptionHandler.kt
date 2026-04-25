package com.hyunbindev.api.exceptionhandler

import com.hyunbindev.auth.exception.AuthException
import com.hyunbindev.common.exception.CommonException
import com.hyunbindev.common.exception.constant.ExceptionCode
import io.swagger.v3.oas.annotations.Parameter
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(AuthException::class)
    fun handleAuthException(exception: AuthException, @Parameter(hidden = true)response: HttpServletResponse): ResponseEntity<ExceptionResponse> {
        if (exception.getStatus() == HttpStatus.UNAUTHORIZED) {
            val cookie = Cookie("JSESSIONID", null).apply {
                path = "/"
                maxAge = 0
                isHttpOnly = true
            }
            response.addCookie(cookie)
        }
        return ResponseEntity
            .status(exception.getStatus())
            .body(ExceptionResponse(
                code = exception.exceptionCode,
                message = exception.message?:""
            ))
    }
    @ExceptionHandler(CommonException::class)
    fun handleCommonException(exception: CommonException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity
            .status(exception.getStatus())
            .body(ExceptionResponse(
                code = exception.exceptionCode,
                message = exception.message?:""
            ))
    }
}

data class ExceptionResponse(val code: ExceptionCode, val message: String)