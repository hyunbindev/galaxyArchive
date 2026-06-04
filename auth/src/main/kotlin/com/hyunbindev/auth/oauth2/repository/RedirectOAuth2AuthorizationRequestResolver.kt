package com.hyunbindev.auth.oauth2.repository

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

class RedirectOAuth2AuthorizationRequestResolver(
    private val defaultResolver: DefaultOAuth2AuthorizationRequestResolver
) : OAuth2AuthorizationRequestResolver {

    private val delimiter = "___"

    override fun resolve(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        val authRequest = defaultResolver.resolve(request)
        return customizeState(request, authRequest)
    }

    override fun resolve(request: HttpServletRequest?, clientRegistrationId: String?): OAuth2AuthorizationRequest? {
        val authRequest = defaultResolver.resolve(request, clientRegistrationId)
        return customizeState(request, authRequest)
    }

    private fun customizeState(
        request: HttpServletRequest?,
        authRequest: OAuth2AuthorizationRequest?
    ): OAuth2AuthorizationRequest? {
        if (authRequest == null || request == null) return null

        val redirectUrl = request.getParameter("redirect")?.takeIf { it.isNotBlank() } ?: "/"

        val originalState = authRequest.state
        val customState = "$originalState$delimiter$redirectUrl"

        return OAuth2AuthorizationRequest.from(authRequest)
            .state(customState)
            .build()
    }
}