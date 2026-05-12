package com.salesyodha.salesyodha_backend.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "bXlzZWNyZXRrZXlteXNlY3JldGtleW15c2VjcmV0MTIz"; // base64

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //  Generate Token with Phone + Role
    public String generateToken(String phoneNumber, String role) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000L * 60 * 60 * 24 * 30)
                )
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //  Extract Phone Number
    public String extractPhoneNumber(String token) {
        return extractAllClaims(token).getSubject();
    }

    //  Extract Role
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //  Validate
    public boolean isTokenValid(String token, String phoneNumber) {
        return extractPhoneNumber(token).equals(phoneNumber)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}