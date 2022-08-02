package com.example.slowdlvy.controller.Member;

import com.example.slowdlvy.controller.Member.dto.MemberRequestDto;
import com.example.slowdlvy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("auth/join")
    public ResponseEntity join(@Validated @RequestBody MemberRequestDto.Join joinRequest){
        return memberService.join(joinRequest);
    }

}
