package com.warriaty.dormbe.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Configuration
public class CorsConfig {

    @Value("${auth.cors.allowed-origin}")
    private String allowedOrigin;

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration());
        return source;
    }

    private CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(singletonList(allowedOrigin));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH", "OPTION"));
        corsConfiguration.setAllowedHeaders(List.of(AUTHORIZATION, CONTENT_TYPE));
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }
}
