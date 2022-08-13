package com.example.slowdlvy.security.service;

import com.example.slowdlvy.Exception.CustomException;
import com.example.slowdlvy.Exception.ErrorCode;
import com.example.slowdlvy.domain.member.Member;
import com.example.slowdlvy.domain.member.MemberRepository;
import com.example.slowdlvy.security.dto.PrincipalDetails;
import com.example.slowdlvy.security.oauth.provider.GoogleUserInfo;
import com.example.slowdlvy.security.oauth.provider.NaverUserInfo;
import com.example.slowdlvy.security.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        String providerType = userRequest.getClientRegistration().getRegistrationId();
        if(providerType.equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(providerType.equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }else {
            throw new CustomException(ErrorCode.PROVIDER_TYPE_ERROR);
        }

        Optional<Member> memberOptional = memberRepository.findByUsername(oAuth2UserInfo.getUsername());
        if(memberOptional.isPresent()){
            throw new CustomException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }else{
            Member member = new Member(oAuth2UserInfo.getUsername(), "slowdlvy",oAuth2UserInfo.getProvider());
            member.setDefaultUser();
            memberRepository.save(member);
            return new PrincipalDetails(member, oAuth2User.getAttributes());
        }

    }
}
