package com.example.slowdlvy.security.jwt;

import com.example.slowdlvy.security.dto.PrincipalDetails;
import com.example.slowdlvy.security.dto.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtManager {

    private static long ACCESS_TOKEN_VALIDATION_SECOND = 60*30;   //30분
    private static long REFRESH_TOKEN_VALIDATEION_SECOND = 60*60*24*30;    //30일
    private static String secretKey = "SecretKeyForJsonWebTokenAtSlowdeliverygkgkgk";
    private static String BEARER_TYPE = "Bearer: ";
    private static SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    //ACCESS TOKEN & REFRESH TOKEN 생성
    public TokenInfo generateToken(Authentication authentication){
        return TokenInfo.builder()
                .accessToken(BEARER_TYPE + generateAccessToken(authentication))
                .refreshToken(BEARER_TYPE + generateRefreshToken())
                .build();
    }

    //ACCESS TOKEN 생성
    private String generateAccessToken(Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Claims claims = Jwts.claims();
        claims.put("username",principalDetails.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATION_SECOND*1000l))
                .signWith(key)
                .compact();
    }

    //REFRESH TOKEN 생성
    private String generateRefreshToken(){
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATION_SECOND*1000l))
                .signWith(key)
                .compact();
    }

}
