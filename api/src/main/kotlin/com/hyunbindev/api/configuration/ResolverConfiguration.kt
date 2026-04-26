package com.hyunbindev.api.configuration

import com.hyunbindev.api.support.resolver.ByteStreamRequestArgumentResolver
import com.hyunbindev.api.support.resolver.ImageRequestArgumentResolver
import com.hyunbindev.api.support.resolver.LoginUserIdArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ResolverConfiguration(
    private val byteStreamRequestArgumentResolver: ByteStreamRequestArgumentResolver,
    private val imageRequestArgumentResolver: ImageRequestArgumentResolver,
    private val loginUserArgumentResolver: LoginUserIdArgumentResolver,
):WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(byteStreamRequestArgumentResolver)
        resolvers.add(imageRequestArgumentResolver)
        resolvers.add(loginUserArgumentResolver)
    }
}