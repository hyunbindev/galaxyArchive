package com.hyunbindev.auth.configuration

import com.hyunbindev.auth.oauth2.service.OAuth2SuccessService
import com.hyunbindev.auth.oauth2.service.OAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint

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
            //accept request
            .authorizeHttpRequests{ auth->
                auth.requestMatchers(
                    "/login/**",
                    "/oauth2/**",
                    "/api/v1/**",
                    "/test/**").permitAll()
                    .anyRequest().authenticated()
            }
            //unauthorized 401 status return
            .exceptionHandling { it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) }
            //oauth2 service
            .oauth2Login { oauth ->
                oauth
                    //load user
                    .userInfoEndpoint { it.userService(oAuth2UserService) }
                    //success handler
                    .successHandler(oAuth2SuccessService)
                    .failureUrl("/oauth2/error")
            }
        return http.build();
    }
}