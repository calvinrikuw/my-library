package com.example.library.app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    private final Key key;
    private final String issuer;
    private final long accessMinutes;

    public JwtUtils(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.issuer}") String issuer,
                    @Value("${jwt.access-token-minutes}") long accessMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.issuer = issuer;
        this.accessMinutes = accessMinutes;
    }

    /**
     * 生成 JWT Token
     *
     */
    public String createAccessToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(accessMinutes * 60)))
                .signWith(key)
                .compact();
    }

    /**
     * 解析 JWT Token
     *
     */
    public Map<String, Object> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

