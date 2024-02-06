package com.petsCare.petsCare.dto.oauth2;

import java.io.Serializable;

public interface OAuth2Response extends Serializable {

    String getProvider();

    String getProviderId();

    String getName();

    String getNickName();

    String getProfileImage();
}
