package com.petsCare.petsCare.repository.user;

import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void 유저_등록() {
        //given
        User user = User.builder()
                .provider("naver")
                .loginId("naver_jinjin")
                .nickName("JJ")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();

        //when
        userRepository.save(user);
        User findUser = userRepository.findById(user.getId()).get();
        int userCount = userRepository.findAll().size();

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        System.out.println("가입 시간 = " + user.getRegisterDate());
        System.out.println("수정 시간 = " + user.getLastLoginDate());
    }

    @Test
    @Transactional
    void 유저_중복_확인() {
        //given
        User user = User.builder()
                .provider("naver")
                .loginId("naver_jinjin")
                .nickName("JJ")
                .profileImage("jj.png")
                .build();
        userRepository.save(user);

        //when
        User findByLoginId = userRepository.findByLoginId("naver_jinjin").get();     // 아이디 중복 확인
        User findByNickName = userRepository.findByNickName("JJ").get();   // 닉네임 중복 확인

        //then
        assertThat(user.getId()).isEqualTo(findByLoginId.getId());
        assertThat(user.getId()).isEqualTo(findByNickName.getId());

    }
}