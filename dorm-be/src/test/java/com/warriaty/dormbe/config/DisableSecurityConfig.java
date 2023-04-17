package com.warriaty.dormbe.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class DisableSecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(User.builder()
                .username("test@example.com")
                .password("test")
                .roles(new String[0])
                .build());
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, AuthenticationManager authManager) throws Exception {
        return security.csrf().disable()
                .authenticationManager(authManager)
                .authorizeHttpRequests().anyRequest().permitAll()
                .and().build();
    }
}
