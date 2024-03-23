package com.dev.webtoonpalette.dto.oauth;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private Map<String, Object> attributes;
    private Map<String, Object> kakaoAccountAttributes;
    private Map<String, Object> profileAttributes;

    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.kakaoAccountAttributes = (Map<String, Object>) attributes.get("properties");
        this.profileAttributes = (Map<String, Object>) attributes.get("profile");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return kakaoAccountAttributes.get("nickname").toString();
    }

    @Override
    public String getName() {
        return kakaoAccountAttributes.get("nickname").toString();
    }

}