package com.hyunbindev.api.support.resolver

import com.hyunbindev.common.image.RequestByteStream
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
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

    private val log = LoggerFactory.getLogger(ByteStreamRequestArgumentResolver::class.java)

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return  parameter.hasParameterAnnotation(RequestByteStream::class.java)
                && InputStream::class.java.isAssignableFrom(parameter.parameterType)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        log.debug("Resolving request to {}", parameter.parameterType)
        val request: HttpServletRequest = webRequest.getNativeRequest(HttpServletRequest::class.java)
            ?:throw IllegalArgumentException("ServletRequest missing")
        return request.inputStream
    }
}