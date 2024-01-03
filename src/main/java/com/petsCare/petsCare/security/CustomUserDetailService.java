package com.petsCare.petsCare.security;

import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailService = " + username);
        Optional<User> user = userRepository.findByLoginId(username);
        user.orElseThrow(
                () -> new InternalAuthenticationServiceException(username + " is not found")
        );
        return new CustomUserDetails(user.get());
    }
}
