package com.warriaty.dormbe.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warriaty.dormbe.auth.jwt.Jwt;
import com.warriaty.dormbe.auth.jwt.JwtProvider;
import com.warriaty.dormbe.auth.model.UserCredentials;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;


import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Component
public class JsonUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private static final String LOGIN_URL = "/api/login";
    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;

    public JsonUsernamePasswordAuthFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.jwtProvider = jwtProvider;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(LOGIN_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException {
        try {
            UserCredentials user = objectMapper.readValue(request.getInputStream(), UserCredentials.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            setDetails(request, token);
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication
    ) throws IOException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Jwt token = jwtProvider.generateToken(principal);
        response.setHeader(CONTENT_TYPE, "application/json");
        objectMapper.writeValue(response.getWriter(), token);
    }
}
