package com.hyunbindev.auth

import com.hyunbindev.auth.oauth2.service.OAuth2SuccessService
import com.hyunbindev.auth.oauth2.service.OAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val oAuth2UserService: OAuth2UserService,
    private val oAuth2SuccessService: OAuth2SuccessService,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf(){ it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests{ auth->
                auth.requestMatchers(
                    "/login/**",
                    "/oauth2/**",
                    "/api/v1/auth/**",
                    "/test/**").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth ->
                oauth
                    .userInfoEndpoint { it.userService(oAuth2UserService) }
                    .successHandler(oAuth2SuccessService)
                    .failureUrl("/oauth2/error")
            }
        return http.build();
    }
}