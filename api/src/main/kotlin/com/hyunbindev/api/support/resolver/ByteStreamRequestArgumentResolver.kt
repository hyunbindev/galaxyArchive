package com.hyunbindev.api.support.resolver

import com.hyunbindev.common.image.RequestByteStream
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.io.InputStream

@Component
class ByteStreamRequestArgumentResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val isValidatedRequest =  parameter.hasParameterAnnotation(RequestByteStream::class.java)
                && InputStream::class.java.isAssignableFrom(parameter.parameterType)

        val servletRequest = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
        val method = servletRequest?.request?.method

        val isSupportedMethod = method in listOf("POST", "PUT", "PATCH")

        return isValidatedRequest && isSupportedMethod
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val request: HttpServletRequest = webRequest.getNativeRequest(HttpServletRequest::class.java)
            ?:throw IllegalArgumentException("ServletRequest missing")
        return request.inputStream
    }
}