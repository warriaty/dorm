package com.warriaty.dormbe.auth.jwt;

import lombok.Data;

@Data
public class Jwt {

    private final String tokenValue;

    private final Long expirationTimestamp;
}