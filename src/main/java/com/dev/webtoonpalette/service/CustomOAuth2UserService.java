package com.dev.webtoonpalette.service;

import com.dev.webtoonpalette.constant.Role;
import com.dev.webtoonpalette.domain.Member;
import com.dev.webtoonpalette.dto.MemberDTO;
import com.dev.webtoonpalette.dto.oauth.*;
import com.dev.webtoonpalette.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("-----------loadUser------------");

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }
        else {
            return null;
        }

        String providerId = oAuth2Response.getProviderId();
        String social =  oAuth2Response.getProvider();
        String nickname = social+"_"+providerId;
        String username = oAuth2Response.getEmail();

        Optional<Member> result = memberRepository.findByProviderId(providerId);

        if(result.isPresent()){
            MemberDTO memberDTO = entityToDTO(result.get());
            log.info("existed.........................."+memberDTO);
            return new CustomOAuth2User(memberDTO);
        }

        Member member = makeMember(username, nickname, social, providerId);
        memberRepository.save(member);
        MemberDTO memberDTO = entityToDTO(member);

        return new CustomOAuth2User(memberDTO);
    }

    public Member makeMember(String username, String nickname, String social, String providerId){

        Member member = Member.builder()
                .username(username)
                .nickname(nickname)
                .role(Role.USER)
                .social(social)
                .providerId(providerId)
                .build();

        return member;
    }


    public MemberDTO entityToDTO (Member member){

        MemberDTO dto = MemberDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .role(member.getRole())
                .social(member.getSocial())
                .providerId(member.getProviderId())
                .build();

        return dto;
    }
}