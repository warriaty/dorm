package com.warriaty.dormbe.auth.filter;


import com.warriaty.dormbe.auth.jwt.JwtService;
import com.warriaty.dormbe.auth.model.UserDetailsImpl;
import com.warriaty.dormbe.auth.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated;

@ExtendWith(MockitoExtension.class)
class JsonWebTokenAuthFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private JwtService tokenProvider;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private JsonWebTokenAuthFilter testObject;


    @Test
    void shouldAttemptJsonTokenAuthenticationWithSettingTheRequestDetails() throws Exception {
        //given
        String token = "XYZ";
        String dummyUserEmail = "dummy@test.com";

        when(request.getHeader(AUTHORIZATION)).thenReturn("Bearer " + token);

        when(tokenProvider.getUsernameFromJwt(token)).thenReturn(dummyUserEmail);

        when(userDetailsService.loadUserByUsername(dummyUserEmail)).thenReturn(new UserDetailsImpl(1L, dummyUserEmail, "password"));

        when(request.getRemoteAddr()).thenReturn("https://test.com");


        //when
        testObject.doFilterInternal(request, response, filterChain);

        //then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isEqualTo(expectedAuth());
        verify(filterChain).doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken expectedAuth() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "dummy@test.com", "password");
        UsernamePasswordAuthenticationToken authentication = authenticated(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}