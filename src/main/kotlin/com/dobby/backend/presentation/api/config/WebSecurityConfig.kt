package com.dobby.backend.presentation.api.config

import com.dobby.backend.domain.exception.PermissionDeniedException
import com.dobby.backend.domain.exception.UnauthorizedException
import com.dobby.backend.infrastructure.token.JwtTokenProvider
import com.dobby.backend.presentation.api.config.filter.JwtAuthenticationFilter
import com.dobby.backend.presentation.api.config.filter.JwtOptionalAuthenticationFilter
import com.dobby.backend.presentation.api.config.properties.AllowedOriginsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
@EnableMethodSecurity
class WebSecurityConfig(
    private val allowedOriginsProperties: AllowedOriginsProperties
) {
    /**
     * Configures the security for authentication-related APIs, including login and signup.
     * These endpoints are publicly accessible without authentication.
     */
    @Bean
    @Order(1)
    fun authSecurityFilterChain(
        httpSecurity: HttpSecurity,
        jwtTokenProvider: JwtTokenProvider,
        handlerExceptionResolver: HandlerExceptionResolver
    ): SecurityFilterChain = httpSecurity
        .securityMatcher("/v1/auth/**",
            "/v1/members/signup/**", "/v1/emails/**",
            "/v1/experiment-posts/counts", "/v1/experiment-posts/search", "/v1/experiment-posts/{postId}/apply-method")
        .csrf { it.disable() }
        .cors(Customizer.withDefaults())
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authorizeHttpRequests {
            it.requestMatchers(
                "/v1/auth/**",
                "/v1/members/signup/**", "/v1/emails/**",
                "/v1/experiment-posts/counts", "/v1/experiment-posts/search", "/v1/experiment-posts/{postId}/apply-method"
            ).permitAll()
            it.anyRequest().authenticated()
        }
        .build()

    /**
     * Configures security for the specific API endpoint.
     * This endpoint is publicly accessible, but if a JWT is provided, it will extract the memberId.
     */
    @Bean
    @Order(2)
    fun publicApiSecurityFilterChain(
        httpSecurity: HttpSecurity,
        jwtTokenProvider: JwtTokenProvider,
        handlerExceptionResolver: HandlerExceptionResolver
    ): SecurityFilterChain = httpSecurity
        .securityMatcher("/v1/experiment-posts/{postId}/details")
        .csrf { it.disable() }
        .cors(Customizer.withDefaults())
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authorizeHttpRequests {
            it.requestMatchers("/v1/experiment-posts/{postId}/details").permitAll()
        }
        .addFilterBefore(
            JwtOptionalAuthenticationFilter(jwtTokenProvider, handlerExceptionResolver),
            UsernamePasswordAuthenticationFilter::class.java
        )
        .build()

    /**
     * Configures security for the remaining APIs.
     * Applies JWT authentication to all APIs except for the ones specified above.
     */
    @Bean
    @Order(3)
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        jwtTokenProvider: JwtTokenProvider,
        handlerExceptionResolver: HandlerExceptionResolver,
    ): SecurityFilterChain = httpSecurity
        .securityMatcher("/v1/**")
        .csrf { it.disable() }
        .cors(Customizer.withDefaults())
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authorizeHttpRequests {
            it.anyRequest().authenticated()
        }
        .addFilterBefore(
            JwtAuthenticationFilter(jwtTokenProvider, handlerExceptionResolver),
            UsernamePasswordAuthenticationFilter::class.java
        )
        .exceptionHandling {
            it.accessDeniedHandler { request, response, exception ->
                handlerExceptionResolver.resolveException(request, response, null, PermissionDeniedException())
            }.authenticationEntryPoint { request, response, authException ->
                handlerExceptionResolver.resolveException(request, response, null, UnauthorizedException())
            }
        }
        .build()

    /**
     * Configures security for Swagger UI and OpenAPI documentation.
     * These endpoints are publicly accessible without authentication.
     */
    @Bean
    @Order(4)
    fun swaggerSecurityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain = httpSecurity
        .securityMatcher("/swagger-ui/**", "/v3/api-docs/**")
        .cors { it.configurationSource(corsConfigurationSource()) }
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
        }
        .build()

    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = allowedOriginsProperties.allowedOrigins
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
