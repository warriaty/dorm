package com.warriaty.dormbe.auth.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("${auth.jwt.expiration.ms}")
    private int jwtExpirationInMs;

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();

    public Jwt generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return new Jwt(Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact(), expiryDate.getTime());
    }

    public String getUsernameFromJwt(String token) throws JwtException {
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }
}
