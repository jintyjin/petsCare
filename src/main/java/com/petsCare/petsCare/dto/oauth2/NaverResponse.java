package com.petsCare.petsCare.dto.oauth2;

import java.util.Map;

public class NaverResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getName() {
        return this.getProvider() + " " + this.getProviderId();
    }

    @Override
    public String getNickName() {
        return attribute.get("nickname").toString();
    }

    @Override
    public String getProfileImage() {
        return attribute.get("profile_image").toString();
    }
}
