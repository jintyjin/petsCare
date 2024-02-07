package com.petsCare.petsCare.service.user;

import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.exception.DuplicatedLoginIdException;
import com.petsCare.petsCare.exception.DuplicatedNickNameException;
import com.petsCare.petsCare.form.user.UserJoinForm;
import com.petsCare.petsCare.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @Transactional
    void 회원_가입_닉네임_중복() {
        //given
        UserJoinForm userJoinForm1 = new UserJoinForm("testMember1234", "testMember!23", "에세이르12");
        UserJoinForm userJoinForm2 = new UserJoinForm("testMember3342", "testMember!23", "에세이르12");

        //when
        userService.joinUser(userJoinForm1);

        // then
        assertThatThrownBy(() -> userService.joinUser(userJoinForm2))
                .isExactlyInstanceOf(DuplicatedNickNameException.class);
    }

    @Test
    @Transactional
    void 회원_가입_아이디_중복() {
        //given
        UserJoinForm userJoinForm1 = new UserJoinForm("testMember1234", "testMember!23", "Eseirss");
        UserJoinForm userJoinForm2 = new UserJoinForm("testMember1234", "testMember!23", "Eseir12");

        //when
        userService.joinUser(userJoinForm1);

        //then
        assertThatThrownBy(() -> userService.joinUser(userJoinForm2))
                .isExactlyInstanceOf(DuplicatedLoginIdException.class);
    }
}