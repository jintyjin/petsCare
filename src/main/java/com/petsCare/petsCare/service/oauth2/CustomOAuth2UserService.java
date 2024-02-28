package com.petsCare.petsCare.service.oauth2;

import com.petsCare.petsCare.dto.oauth2.CustomOAuth2User;
import com.petsCare.petsCare.dto.oauth2.GoogleResponse;
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
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String role = "ROLE_USER";
        String loginId = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        Optional<User> findUser = userRepository.findByLoginId(loginId);
        User user = null;
        if (findUser.isEmpty()) {
            user = User.builder()
                    .provider(oAuth2Response.getProvider())
                    .loginId(loginId)
                    .username(oAuth2Response.getName())
                    .profileImage(oAuth2Response.getProfileImage())
                    .role(role)
                    .build();

            userRepository.save(user);
        } else {
            user = findUser.get();
        }

        return new CustomOAuth2User(user);
    }
}
