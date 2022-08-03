package com.example.slowdlvy.controller.Member;

import com.example.slowdlvy.controller.Member.dto.MemberRequestDto;
import com.example.slowdlvy.security.dto.TokenInfo;
import com.example.slowdlvy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("auth/join")
    public ResponseEntity join(@Validated @RequestBody MemberRequestDto.Join joinRequest){
        return memberService.join(joinRequest);
    }

    @PostMapping("auth/login")
    public ResponseEntity login(@Validated @RequestBody MemberRequestDto.Login loginRequest, HttpServletResponse response){
        TokenInfo tokenInfo = memberService.login(loginRequest);
        response.addHeader("ACCESSTOKEN", tokenInfo.getAccessToken());
        response.addHeader("REFRESHTOKEN", tokenInfo.getRefreshToken());
        return ResponseEntity.ok("로그인되었습니다.");
    }
}
