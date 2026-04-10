package com.hyunbindev.api.configuration

import com.hyunbindev.api.support.resolver.ByteStreamRequestArgumentResolver
import com.hyunbindev.api.support.resolver.ImageRequestArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ResolverConfiguration(
    private val byteStreamRequestArgumentResolver: ByteStreamRequestArgumentResolver,
    private val imageRequestArgumentResolver: ImageRequestArgumentResolver
):WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(byteStreamRequestArgumentResolver)
        resolvers.add(imageRequestArgumentResolver)
    }
}