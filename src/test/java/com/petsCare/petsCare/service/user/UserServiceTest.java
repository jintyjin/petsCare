package com.petsCare.petsCare.service.user;

import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.form.user.UserJoinForm;
import com.petsCare.petsCare.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
    void BCrypt_테스트() {
        //given
        String pwd = "비밀번호123";

        //when
        String encodePwd = passwordEncoder.encode(pwd);
        User user = new User("test", encodePwd, "테스트");
        userRepository.save(user);
        User findUser = userRepository.findByLoginId(user.getLoginId()).get();

        //then
        assertThat(BCrypt.checkpw(pwd, findUser.getPassword())).isTrue();
    }

    @Test
    @Transactional
    void 회원_가입() {
        //given
        UserJoinForm userJoinForm1 = new UserJoinForm("testMember1234", "testMember!23", "Eseir");
        UserJoinForm userJoinForm2 = new UserJoinForm("testMember3342", "testMember!23", "Eseir");

        //when //then
        assertThatCode(() -> userService.joinUser(userJoinForm1));
        assertThatThrownBy(() -> userService.joinUser(userJoinForm2));
    }
}