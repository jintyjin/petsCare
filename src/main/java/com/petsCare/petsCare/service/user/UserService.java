package com.petsCare.petsCare.service.user;

import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.exception.DuplicatedLoginIdException;
import com.petsCare.petsCare.exception.DuplicatedNickNameException;
import com.petsCare.petsCare.form.user.UserJoinForm;
import com.petsCare.petsCare.repository.user.UserRepository;
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
        User user = userJoinForm.createUser(passwordEncoder);

        userRepository
                .findByLoginId(user.getLoginId())
                .ifPresent(byLoginId -> {
                    throw new DuplicatedLoginIdException("existUser.userJoinForm.loginId");
                });

        userRepository
                .findByNickName(user.getNickName())
                .ifPresent(byNickName -> {
                    throw new DuplicatedNickNameException("existUser.userJoinForm.nickName");
                });

        userRepository.save(user);
    }
}
