package com.petsCare.petsCare.oAuth2.dto;

import com.petsCare.petsCare.user.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User, Serializable {

    private final UserDto user;

    public CustomOAuth2User(UserDto user) {
        this.user = user;
    }

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
                return user.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return user.getLoginId();
    }

    public UserDto getUserDto() {
        return this.user;
    }
}
