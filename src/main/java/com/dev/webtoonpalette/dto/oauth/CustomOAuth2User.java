package com.dev.webtoonpalette.dto.oauth;

import com.dev.webtoonpalette.constant.Role;
import com.dev.webtoonpalette.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final MemberDTO memberDTO;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberDTO.getRole().toString();
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return memberDTO.getNickname();
    }

    public String getUsername() {
        return memberDTO.getUsername();
    }

    public String getProviderId() {
        return memberDTO.getProviderId();
    }

    public String getSocial(){
        return memberDTO.getSocial();
    }

    public Role getRole(){
        return memberDTO.getRole();
    }

    public Long getId() {
        return memberDTO.getId();
    }

    public String getUploadFileName() {
        return memberDTO.getUploadFileName();
    }

}
