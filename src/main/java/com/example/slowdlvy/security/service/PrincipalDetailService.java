package com.example.slowdlvy.security.service;

import com.example.slowdlvy.Exception.CustomException;
import com.example.slowdlvy.Exception.ErrorCode;
import com.example.slowdlvy.domain.member.Member;
import com.example.slowdlvy.domain.member.MemberRepository;
import com.example.slowdlvy.security.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    /**
     * 예외처리 확인해주기
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("해당하는 유저가 없습니다."));
        return new PrincipalDetails(member);
    }
}
