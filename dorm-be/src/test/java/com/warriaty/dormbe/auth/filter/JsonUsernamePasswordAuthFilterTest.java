package com.warriaty.dormbe.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warriaty.dormbe.auth.jwt.Jwt;
import com.warriaty.dormbe.auth.jwt.JwtService;
import com.warriaty.dormbe.auth.model.UserCredentials;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;
import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@ExtendWith(MockitoExtension.class)
class JsonUsernamePasswordAuthFilterTest {

    private static final WebAuthenticationDetailsSource AUTH_DETAILS_SOURCE = new WebAuthenticationDetailsSource();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Authentication authentication;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ServletInputStream servletInputStream;

    @Mock
    private JwtService jwtService;

    @Mock
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<UsernamePasswordAuthenticationToken> tokenCaptor;

    @InjectMocks
    private JsonUsernamePasswordAuthFilter testObject;

    @Test
    void shouldAttemptJsonAuthenticationWithSettingTheRequestDetails() throws IOException {
        //given
        UserCredentials userCredentials = new UserCredentials("test@test.com", "1234");

        when(request.getInputStream()).thenReturn(servletInputStream);
        when(objectMapper.readValue(servletInputStream, UserCredentials.class)).thenReturn(userCredentials);
        when(request.getRemoteAddr()).thenReturn("https://test.com");

        //when
        testObject.attemptAuthentication(request, response);

        //then
        verify(authenticationManager).authenticate(tokenCaptor.capture());
        assertThat(tokenCaptor.getValue()).isEqualTo(expectedToken());
    }

    private UsernamePasswordAuthenticationToken expectedToken() {
        var token = new UsernamePasswordAuthenticationToken("test@test.com", "1234");
        token.setDetails(AUTH_DETAILS_SOURCE.buildDetails(request));
        return token;
    }

    @Test
    void shouldReturnTokenInResponseOnSuccessfulAuthentication() throws IOException {
        //given
        UserDetails userDetails = mock(UserDetails.class);
        Jwt token = mock(Jwt.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(token);
        when(response.getWriter()).thenReturn(writer);

        //when
        testObject.successfulAuthentication(request, response, filterChain, authentication);

        //then
        verify(response).setHeader(CONTENT_TYPE, "application/json");
        verify(objectMapper).writeValue(writer, token);
    }

    @Test
    void shouldNotCompromiseAnySensitiveInformationToNotLoggedInUser() throws IOException {
        //given
        when(request.getInputStream()).thenReturn(servletInputStream);
        when(objectMapper.readValue(servletInputStream, UserCredentials.class)).thenThrow(new IOException("test"));

        //when
        var exc = assertThrows(IllegalArgumentException.class, () -> testObject.attemptAuthentication(request, response));

        //then
        assertThat(exc).hasMessage(null);
    }
}