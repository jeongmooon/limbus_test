package com.example.test_web.global.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
//    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String SECRET = "my-custom-secret-keymy-custom-secret-key"; // 최소 32바이트
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public static String generateAccessToken(String userId,String deviceId, String userType) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("tokenType", "access")
                .claim("deviceId", deviceId)
                .claim("userType", userType)
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15분
                .signWith(key)
                .compact();
    }

    public static String generateRefreshToken(String userId,String deviceId) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("tokenType", "refresh")
                .claim("deviceId", deviceId)
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7일
                .signWith(key)
                .compact();
    }

    public static String getTokenClaim(String token, String claimType){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody()
                .get(claimType, String.class);
    }

    public static String validate(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}
