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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val oAuth2UserService: OAuth2UserService,
    private val oAuth2SuccessService: OAuth2SuccessService,
) {
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowedOrigins = listOf("http://localhost:3000")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf(){ it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            //accept request
            .authorizeHttpRequests{ auth->
                auth.requestMatchers(
                    "/login/**",
                    "/oauth2/**",
                    "/api/v1/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
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
            //log outin
            .logout { logout->
                logout.logoutUrl("/api/v1/auth/logout")
                    .logoutSuccessHandler{_,response,_ ->
                        response.status = HttpStatus.OK.value()
                    }
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
            }
        return http.build();
    }
}