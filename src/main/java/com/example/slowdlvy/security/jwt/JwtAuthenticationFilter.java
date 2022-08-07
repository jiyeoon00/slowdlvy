package com.example.slowdlvy.security.jwt;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;

    /**
     * 토큰 검증해서 정삭적인 사용자이면 인증객체 만들어 넣어주고 필터 타도록,
     *             비정상적인 사용자이면 그냥 필터 타도록
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtManager.extractTokenFromHeader(request);
        if(token != null && jwtManager.isValidate(token)){
            Authentication authentication = jwtManager.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }


}
