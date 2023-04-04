package com.warriaty.dormbe.auth.config;

import com.warriaty.dormbe.auth.filter.JsonUsernamePasswordAuthFilter;
import com.warriaty.dormbe.auth.filter.JsonWebTokenAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class FilterChainConfig {

    private final UrlBasedCorsConfigurationSource corsSource;

    private final JsonUsernamePasswordAuthFilter jsonUsernamePasswordAuthFilter;

    private final JsonWebTokenAuthFilter jsonWebTokenAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security.csrf().disable();
        security.httpBasic().disable();
        security.formLogin().disable();
        security.logout().disable();

        security.authorizeHttpRequests()
                .requestMatchers("/api/login", "/api/users/register")
                .permitAll()
                .anyRequest()
                .authenticated();

        security.addFilterBefore(jsonWebTokenAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(jsonUsernamePasswordAuthFilter);

        security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        security.cors().configurationSource(corsSource);

        return security.build();
    }
}
