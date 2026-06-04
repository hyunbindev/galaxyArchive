package com.hyunbindev.auth.oauth2.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Service

@Service
class OAuth2SuccessService(
    @param:Value("\${app.domain-url}") private val frontendUrl: String
) : SimpleUrlAuthenticationSuccessHandler(){

    private val delimiter = "___"

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val state = request.getParameter("state")

        val targetUrl = state?.takeIf { it.contains(delimiter) }
            ?.split(delimiter)
            ?.getOrNull(1) ?: "/"

        // TODO: 오픈 리다이렉트(Open Redirect) 방지를 위한 화이트리스트 호스트 검증 로직 추가 필요

        redirectStrategy.sendRedirect(request, response, frontendUrl+targetUrl)
    }
}