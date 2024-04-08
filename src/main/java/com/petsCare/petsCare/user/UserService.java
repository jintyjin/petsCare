package com.petsCare.petsCare.user;

import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.user.dto.form.UserJoinForm;
import com.petsCare.petsCare.user.exception.UserException;
import com.petsCare.petsCare.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void joinUser(UserJoinForm userJoinForm) {
        userRepository
                .findByLoginId(userJoinForm.getLoginId())
                .ifPresent(byLoginId -> {
                    throw UserException.USER_DUPLICATED_EXCEPTION;
                });

        User user = User.builder()
                .loginId(userJoinForm.getLoginId())
                .username(userJoinForm.getUsername())
                .build();

        userRepository.save(user);
    }
}
