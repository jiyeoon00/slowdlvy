package com.example.slowdlvy.security.jwt;

import com.example.slowdlvy.Exception.CustomException;
import com.example.slowdlvy.Exception.ErrorCode;
import com.example.slowdlvy.security.dto.PrincipalDetails;
import com.example.slowdlvy.security.dto.TokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

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
                .refreshToken(BEARER_TYPE + generateRefreshToken(authentication))
                .build();
    }

    //ACCESS TOKEN 생성
    private String generateAccessToken(Authentication authentication){
        return doGenerateToken(authentication, ACCESS_TOKEN_VALIDATION_SECOND);
    }

    //REFRESH TOKEN 생성
    private String generateRefreshToken(Authentication authentication){
        return doGenerateToken(authentication, ACCESS_TOKEN_VALIDATION_SECOND);
    }

    private String doGenerateToken(Authentication authentication, Long validationTime){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String authorities = principalDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims();
        claims.put("username",principalDetails.getUsername());
        claims.put("userrole",authorities);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validationTime*1000l))
                .signWith(key)
                .compact();
    }

    /**
     * 토큰해석
     */
    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();
    }

    /**
     * 토큰 유효한지
     */
    public boolean isValidate(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException | io.jsonwebtoken.security.SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e){
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        Claims claims = extractClaims(token);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("userrole").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(claims.get("username"), null, authorities);
    }
}
