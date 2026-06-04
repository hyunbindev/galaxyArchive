package com.hyunbindev.auth.configuration

import com.hyunbindev.auth.oauth2.repository.RedirectOAuth2AuthorizationRequestResolver
import com.hyunbindev.auth.oauth2.service.OAuth2SuccessService
import com.hyunbindev.auth.oauth2.service.OAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val oAuth2UserService: OAuth2UserService,
    private val oAuth2SuccessService: OAuth2SuccessService,
    private val clientRegistrationRepository: ClientRegistrationRepository
) {
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:3000")
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }

            // Accept requests
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    "/login/**",
                    "/oauth2/**",
                    "/api/v1/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/test/**"
                ).permitAll()
                    .anyRequest().authenticated()
            }

            // Unauthorized 401 status return
            .exceptionHandling { it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) }

            // OAuth2 Service
            .oauth2Login { oauth ->
                // 기본 리졸버 생성 (주입받은 repository와 기본 매핑 주소 넣어줌)
                val defaultResolver = DefaultOAuth2AuthorizationRequestResolver(
                    clientRegistrationRepository,
                    "/oauth2/authorization"
                )

                oauth
                    .userInfoEndpoint { it.userService(oAuth2UserService) }
                    .successHandler(oAuth2SuccessService)
                    .failureUrl("/oauth2/error")
                    .authorizationEndpoint { authorization ->
                        authorization.authorizationRequestResolver(
                            RedirectOAuth2AuthorizationRequestResolver(defaultResolver)
                        )
                    }
            }

            // Log out
            .logout { logout ->
                logout.logoutUrl("/api/v1/auth/logout")
                    .logoutSuccessHandler { _, response, _ ->
                        response.status = HttpStatus.OK.value()
                    }
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
            }

        return http.build()
    }
}