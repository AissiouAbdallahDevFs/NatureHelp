package com.NatureHelp.api.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NatureHelp.api.Config.JwtConfig;

import java.util.Date;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenService {

    @Autowired
    private JwtConfig jwtConfig;

    public String generateJwtToken(String email, String password) {
        long expirationTimeInMillis = System.currentTimeMillis() + jwtConfig.getTokenExpirationMillis();

        String token = Jwts.builder()
            .setSubject(email)
            .setId(UUID.randomUUID().toString())
            .setExpiration(new Date(expirationTimeInMillis))
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(5l, ChronoUnit.MINUTES)))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getJwtSecret().getBytes())
            .compact();
        return token;
    }
    
    public Claims decodeJwt(String jwtToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtConfig.getJwtSecret().getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwtToken.replace("Bearer ", ""))
                    .getBody();
        } catch (JwtException e) {
            return null;
        }
    }
    
}
     