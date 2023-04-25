package com.warriaty.dormbe.auth.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
@RequiredArgsConstructor
public class JwtService implements JwtProvider, JwtExtractor {

    private final static SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    final static JwtParser JWT_PARSER = Jwts.parserBuilder().setSigningKey(KEY).build();

    private final int jwtExpirationInMs;

    public Jwt generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return new Jwt(Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(KEY)
                .compact(), expiryDate.getTime());
    }

    public String getUsernameFromJwt(String token) throws JwtException {
        return JWT_PARSER.parseClaimsJws(token).getBody().getSubject();
    }
}
