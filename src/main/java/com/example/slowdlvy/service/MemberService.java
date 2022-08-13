package com.example.slowdlvy.service;

import com.example.slowdlvy.Exception.CustomException;
import com.example.slowdlvy.Exception.ErrorCode;
import com.example.slowdlvy.Exception.ErrorResponse;
import com.example.slowdlvy.controller.Member.dto.MemberRequestDto;
import com.example.slowdlvy.domain.member.Member;
import com.example.slowdlvy.domain.member.MemberRepository;
import com.example.slowdlvy.domain.member.Provider;
import com.example.slowdlvy.security.dto.PrincipalDetails;
import com.example.slowdlvy.security.dto.TokenInfo;
import com.example.slowdlvy.security.jwt.JwtManager;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtManager jwtManager;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public ResponseEntity<?> join(MemberRequestDto.Join joinRequest){
        if(memberRepository.existsByUsername(joinRequest.getUsername())){
            return ErrorResponse.toResponse(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        Member member = new Member(joinRequest.getUsername(), passwordEncoder.encode(joinRequest.getPassword()),Provider.LOCAL);
        member.setDefaultUser();

        memberRepository.save(member);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @Transactional
    public TokenInfo login(MemberRequestDto.Login loginRequest){

        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //SecurityContextHolder.getContext().setAuthentication(authenticate);
        /**
         * 토큰 발급 후 response 헤더에 넣어주기
         */
        TokenInfo tokenInfo = jwtManager.generateToken(authenticate);
        String refreshToken = tokenInfo.extractRefreshToken();
        refreshTokenService.saveOrUpdateRefreshToken(loginRequest.getUsername(), refreshToken);

        return tokenInfo;
    }

    @Transactional
    public TokenInfo reissue(HttpServletRequest request){
        String refreshToken = jwtManager.extractRefreshTokenFromHeader(request);
        if(refreshToken != null && jwtManager.isValidate(refreshToken)) {
            Authentication authentication = jwtManager.getAuthentication(refreshToken);
            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
            if(refreshTokenService.refreshTokenIsExist(principal.getUsername(), refreshToken)) {
                TokenInfo tokenInfo = jwtManager.generateToken(authentication);
                refreshTokenService.saveOrUpdateRefreshToken(principal.getUsername(), refreshToken);
                return tokenInfo;
            }
            throw new CustomException(ErrorCode.INVAILD_REFRESH_TOKEN);
        }
        throw new CustomException(ErrorCode.INVAILD_REFRESH_TOKEN);
    }

    @Transactional
    public void logout(String username){
        SecurityContextHolder.clearContext();
        refreshTokenService.deleteRefreshToken(username);
    }
}
