package com.hyunbindev.api.support.resolver

import com.hyunbindev.auth.application.port.UserProviderUseCase
import com.hyunbindev.auth.exception.AuthException
import com.hyunbindev.auth.exception.constant.AuthExceptionCode
import com.hyunbindev.common.auth.LoginUserId
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.UUID

//TODO: Extract to common-web module
@Component
class LoginUserIdArgumentResolver(
    private val userProvider: UserProviderUseCase
): HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUserId::class.java) && parameter.parameterType == UUID::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): UUID? {
        val annotation = parameter.getParameterAnnotation(LoginUserId::class.java)
            ?:throw Exception("@LoginUserId annotation is required")

        val userId = userProvider.getLoginUserId()

        return if (annotation.required) {
            userId ?: throw AuthException(AuthExceptionCode.USER_UNAUTHORIZED)

        } else {
            userId
        }
    }
}