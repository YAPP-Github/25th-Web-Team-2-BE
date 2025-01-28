package com.dobby.backend.presentation.api.config

import com.dobby.backend.presentation.api.config.properties.AllowedOriginsProperties
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@RequiredArgsConstructor
class WebConfig(
    private val allowedOriginsProperties: AllowedOriginsProperties
) : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        val allowedOrigins = allowedOriginsProperties.allowedOrigins

        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins(*allowedOrigins.toTypedArray())
            .allowCredentials(true)

        registry.addMapping("/swagger-ui/**")
            .allowedMethods("*")
            .allowedOrigins(*allowedOrigins.toTypedArray())
            .allowCredentials(true)

        registry.addMapping("/v3/api-docs/**")
            .allowedMethods("*")
            .allowedOrigins(*allowedOrigins.toTypedArray())
            .allowCredentials(true)
    }
}
