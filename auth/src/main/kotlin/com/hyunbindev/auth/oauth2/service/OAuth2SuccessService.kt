package com.hyunbindev.auth.oauth2.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Service

@Service
class OAuth2SuccessService : SimpleUrlAuthenticationSuccessHandler(){

    override fun onAuthenticationSuccess(
        request: HttpServletRequest ,response: HttpServletResponse ,authentication: Authentication) {
        redirectStrategy.sendRedirect(request, response, "/test")
    }
}