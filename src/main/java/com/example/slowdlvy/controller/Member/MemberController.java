package com.example.slowdlvy.controller.Member;

import com.example.slowdlvy.controller.Member.dto.MemberRequestDto;
import com.example.slowdlvy.security.dto.PrincipalDetails;
import com.example.slowdlvy.security.dto.TokenInfo;
import com.example.slowdlvy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("auth/reissue")
    public ResponseEntity reissue(HttpServletRequest request, HttpServletResponse response){
        TokenInfo reissue = memberService.reissue(request);
        response.addHeader("ACCESSTOKEN", reissue.getAccessToken());
        response.addHeader("REFRESHTOKEN", reissue.getRefreshToken());
        return ResponseEntity.ok("토큰이 재발행 되었습니다.");
    }

    @PostMapping("auth/logout")
    public ResponseEntity logout(@AuthenticationPrincipal PrincipalDetails principalDetails){
        memberService.logout(principalDetails.getUsername());
        return ResponseEntity.ok("로그아웃 완료");
    }

    @GetMapping("auth/success")
    public ResponseEntity success(){
        return ResponseEntity.ok("로그인 되었습니다.");
    }

//
//    @GetMapping("home")
//    public ResponseEntity home(@AuthenticationPrincipal PrincipalDetails principalDetails){
//        System.out.println(principalDetails.getUsername());
//        System.out.println(principalDetails.getAuthorities());
//
//        return ResponseEntity.ok("home입니다.");
//    }
}
