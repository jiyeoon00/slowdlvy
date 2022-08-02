package com.example.slowdlvy.service;

import com.example.slowdlvy.Exception.ErrorCode;
import com.example.slowdlvy.Exception.ErrorResponse;
import com.example.slowdlvy.controller.Member.dto.MemberRequestDto;
import com.example.slowdlvy.domain.member.Member;
import com.example.slowdlvy.domain.member.MemberRepository;
import com.example.slowdlvy.domain.member.Provider;
import com.example.slowdlvy.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<?> join(MemberRequestDto.Join joinRequest){
        if(memberRepository.existsByUsername(joinRequest.getUsername())){
            return ErrorResponse.toResponse(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                    .username(joinRequest.getUsername())
                    .password(passwordEncoder.encode(joinRequest.getPassword()))
                    .nickname("유저_"+(char)((int)(Math.random()*26)+97))
                    .role(Role.USER)
                    .provider(Provider.LOCAL)
                    .build();

        memberRepository.save(member);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }
}
