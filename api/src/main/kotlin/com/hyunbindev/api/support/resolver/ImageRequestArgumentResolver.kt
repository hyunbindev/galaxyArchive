package com.hyunbindev.api.support.resolver

import com.hyunbindev.common.image.ImageExtension
import com.hyunbindev.common.image.ImageUploadMetadata
import com.hyunbindev.common.image.UploadImageHeader
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import kotlin.jvm.java
@Component
class ImageRequestArgumentResolver:HandlerMethodArgumentResolver {

    private val log = LoggerFactory.getLogger(ImageRequestArgumentResolver::class.java)

    override fun supportsParameter(parameter: MethodParameter): Boolean {

        val hasAnnotation = parameter.hasParameterAnnotation(UploadImageHeader::class.java)

        if (!hasAnnotation) return false

        if (!ImageUploadMetadata::class.java.isAssignableFrom(parameter.parameterType)) {
            throw IllegalArgumentException("IllegalArgument parameter type: ${parameter.parameterType}")
        }


        return true
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        log.debug("Resolving request from {}", parameter.parameterType)


        val contentTypeRaw = webRequest.getHeader(ImageUploadMetadata.Headers.CONTENT_TYPE)
            ?: throw IllegalArgumentException("${ImageUploadMetadata.Headers.CONTENT_TYPE} empty header")

        val contentLength = webRequest.getHeader(ImageUploadMetadata.Headers.CONTENT_LENGTH)?.toLong()
            ?: throw IllegalArgumentException("${ImageUploadMetadata.Headers.CONTENT_LENGTH} empty header")

        val extension = try {
            ImageExtension.from(contentTypeRaw)
        } catch (e: Exception) {
            throw e
        }

        return ImageUploadMetadata(
            contentType = extension,
            contentLength = contentLength
        )
    }
}