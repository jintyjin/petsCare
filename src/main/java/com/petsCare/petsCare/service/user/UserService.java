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
        userRepository
                .findByLoginId(userJoinForm.getLoginId())
                .ifPresent(byLoginId -> {
                    throw new DuplicatedLoginIdException("이미 존재하는 아이디입니다.");
                });

        userRepository
                .findByNickName(userJoinForm.getNickName())
                .ifPresent(byNickName -> {
                    throw new DuplicatedNickNameException("이미 존재하는 닉네임입니다.");
                });

        User user = User.builder()
                .loginId(userJoinForm.getLoginId())
                .nickName(userJoinForm.getNickName())
                .build();

        userRepository.save(user);
    }
}
