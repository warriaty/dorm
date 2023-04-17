package com.warriaty.dormbe.auth.jwt;

public interface JwtExtractor {

    String getUsernameFromJwt(String jwt);
}
