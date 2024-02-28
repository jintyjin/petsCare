package com.petsCare.petsCare.service.user;

import com.petsCare.petsCare.exception.DuplicatedLoginIdException;
import com.petsCare.petsCare.form.user.UserJoinForm;
import com.petsCare.petsCare.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    @Transactional
    void 회원_가입_아이디_중복() {
        //given
        UserJoinForm userJoinForm = new UserJoinForm("testMember1234", "testMember!23", "Eseir12");

        Mockito.when(userRepository.findByLoginId(Mockito.any(String.class)))
                .thenThrow(DuplicatedLoginIdException.class);

        //when //then
        assertThatThrownBy(() -> userService.joinUser(userJoinForm))
                .isExactlyInstanceOf(DuplicatedLoginIdException.class);
    }
}