package com.example.slowdlvy.security.jwt;

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
        String token = extractTokenFromHeader(request);
        if(token != null && jwtManager.isValidate(token)){
            Authentication authentication = jwtManager.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    //토큰이 비어있는지 or Bearer 형식인지 확인 후 토큰 추출해주는 메서드
    private String extractTokenFromHeader(HttpServletRequest request){
        String accesstoken = request.getHeader("ACCESSTOKEN");
        if(accesstoken == null || !accesstoken.startsWith("Bearer")){
            return null;
        }else{
            return accesstoken.replace("Bearer: ", "");
        }
    }
}
