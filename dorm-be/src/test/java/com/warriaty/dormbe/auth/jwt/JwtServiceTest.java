package com.warriaty.dormbe.auth.jwt;

import com.warriaty.dormbe.auth.model.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class JwtServiceTest {

    private final JwtService testObject = new JwtService(10000);

    @Test
    void shouldGenerateToken() {
        //given
        var userDetails = new UserDetailsImpl(1L, "user@test.com", "password");

        //when
        var token = testObject.generateToken(userDetails);

        //then
        var body = JwtService.JWT_PARSER.parseClaimsJws(token.getTokenValue()).getBody();
        assertThat(body.getSubject()).isEqualTo("user@test.com");
        assertThat(body.getIssuedAt()).isCloseTo(now(), 5000);
        int jwtExpirationInMs = 10000;
        assertThat(body.getExpiration()).isCloseTo(now().plusMillis(jwtExpirationInMs), 5000);
    }

    @Test
    void shouldGetUsernameFromJWT() {
        //given
        var token = testObject.generateToken(new UserDetailsImpl(1L, "user@test.com", "password"));

        //when
        var username = testObject.getUsernameFromJwt(token.getTokenValue());

        //then
        assertThat(username).isEqualTo("user@test.com");
    }

    @Test
    void shouldInvalidateToken() {
        //given
        setField(testObject, "jwtExpirationInMs", 1);
        var token = testObject.generateToken(new UserDetailsImpl(1L, "user@test.com", "password"));

        //when
        var exc = assertThrows(JwtException.class,() -> testObject.getUsernameFromJwt(token.getTokenValue()));

        //then
        assertThat(exc).hasMessageContaining("JWT expired at");
    }

    @Test
    void shouldHandleNullToken() {
        //given
        String tokenString = null;

        //when
        var exc = assertThrows(IllegalArgumentException.class,() -> testObject.getUsernameFromJwt(tokenString));

        //then
        assertThat(exc).hasMessage("JWT String argument cannot be null or empty.");
    }
}