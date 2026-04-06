package com.hyunbindev.api.support.resolver

import com.hyunbindev.common.image.ImageExtension
import com.hyunbindev.common.image.ImageUploadMetadata
import com.hyunbindev.common.image.UploadImageHeader
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import kotlin.jvm.java

class ImageRequestArgumentResolver:HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(UploadImageHeader::class.java)
                &&ImageUploadMetadata::class.java.isAssignableFrom(parameter.parameterType)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {

        val originalName = webRequest.getHeader(ImageUploadMetadata.Headers.ORIGINAL_NAME)
            ?: throw IllegalArgumentException("[${ImageUploadMetadata.Headers.ORIGINAL_NAME}] empty header")

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
            originalName = originalName,
            contentType = extension,
            contentLength = contentLength
        )
    }
}