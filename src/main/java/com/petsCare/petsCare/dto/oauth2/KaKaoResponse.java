package com.petsCare.petsCare.dto.oauth2;

import java.util.Map;

public class KaKaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public KaKaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getNickName() {
        return ((Map<String, Object>) attribute.get("properties")).get("nickname").toString();
    }

    @Override
    public String getProfileImage() {
        return ((Map<String, Object>) attribute.get("properties")).get("profile_image").toString();
    }
}
