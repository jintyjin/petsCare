package com.petsCare.petsCare.user.service;

import com.petsCare.petsCare.user.UserService;
import com.petsCare.petsCare.user.dto.form.UserJoinForm;
import com.petsCare.petsCare.user.exception.UserException;
import com.petsCare.petsCare.user.repository.UserRepository;
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
                .thenThrow(UserException.DUPLICATED_LOGIN_ID_EXCEPTION);

        //when //then
        assertThatThrownBy(() -> userService.joinUser(userJoinForm))
                .isExactlyInstanceOf(UserException.class);
    }
}