package com.petsCare.petsCare.user.repository;

import com.petsCare.petsCare.user.entity.DeletedUser;
import com.petsCare.petsCare.user.entity.User;
import com.petsCare.petsCare.user.repository.DeletedUserRepository;
import com.petsCare.petsCare.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DeletedUserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    DeletedUserRepository deletedUserRepository;

    @Test
    void 유저_탈퇴() {
        //given
        User user = User.builder()
                .provider("naver")
                .loginId("naver_jinjin")
                .username("JJ")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
        DeletedUser deletedUser = DeletedUser.builder()
                .provider("naver")
                .loginId("naver_jinjin")
                .username("JJ")
                .profileImage("jj.png")
                .role("ROLE_USER")
                .build();

        //when
        deletedUserRepository.save(deletedUser);
        userRepository.delete(user);
        DeletedUser findDeletedUser = deletedUserRepository.findByLoginId(deletedUser.getLoginId()).get();
        Optional<User> findUser = userRepository.findByLoginId(user.getLoginId());

        //then
        assertThat(findDeletedUser.getLoginId()).isEqualTo(user.getLoginId());
        System.out.println("탈퇴 시간 : " + findDeletedUser.getDeletedDate());
        assertThat(findUser.isPresent()).isFalse();
    }
}