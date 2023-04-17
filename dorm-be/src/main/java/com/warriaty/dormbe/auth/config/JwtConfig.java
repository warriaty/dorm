package com.warriaty.dormbe.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warriaty.dormbe.auth.filter.JsonUsernamePasswordAuthFilter;
import com.warriaty.dormbe.auth.filter.JsonWebTokenAuthFilter;
import com.warriaty.dormbe.auth.jwt.JwtExtractor;
import com.warriaty.dormbe.auth.jwt.JwtProvider;
import com.warriaty.dormbe.auth.jwt.JwtService;
import com.warriaty.dormbe.auth.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    @Value("${auth.jwt.expiration.ms}")
    private int jwtExpirationInMs;

    private final ObjectMapper objectMapper;

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthenticationManager authManager;

    @Bean
    public JwtService jwtProvider() {
        return new JwtService(jwtExpirationInMs);
    }

    @Bean
    JsonUsernamePasswordAuthFilter jsonUsernamePasswordAuthFilter(JwtProvider jwtService) {
        return new JsonUsernamePasswordAuthFilter(objectMapper, authManager, jwtService);
    }

    @Bean
    JsonWebTokenAuthFilter jsonWebTokenAuthFilter(JwtExtractor jwtService) {
        return new JsonWebTokenAuthFilter(jwtService, userDetailsService);
    }
}
