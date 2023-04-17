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

    private final static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final static JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();

    private final int jwtExpirationInMs;

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
