package com.NatureHelp.api.Config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMillis}")
    private long tokenExpirationMillis;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public long getTokenExpirationMillis() {
        return tokenExpirationMillis;
    }



        
  
}

