package com.warriaty.dormbe.auth.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtProvider {

    Jwt generateToken(UserDetails userDetails);
}
