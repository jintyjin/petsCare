package com.petsCare.petsCare.service.oauth2;

import com.petsCare.petsCare.dto.oauth2.CustomOAuth2User;
import com.petsCare.petsCare.dto.oauth2.KaKaoResponse;
import com.petsCare.petsCare.dto.oauth2.NaverResponse;
import com.petsCare.petsCare.dto.oauth2.OAuth2Response;
import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {
            oAuth2Response = new KaKaoResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String role = "ROLE_USER";
        Optional<User> findUser = userRepository.findByLoginId(oAuth2Response.getLoginId());
        if (findUser.isEmpty()) {
            User user = User.builder()
                    .provider(oAuth2Response.getProvider())
                    .loginId(oAuth2Response.getLoginId())
                    .nickName(oAuth2Response.getNickName())
                    .profileImage(oAuth2Response.getProfileImage())
                    .role(role)
                    .build();

            userRepository.save(user);
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}
