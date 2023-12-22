package com.petsCare.petsCare.validation;

import com.petsCare.petsCare.form.user.UserJoinForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BeanValidationTest {

    @Autowired Validator validator;

    @Test
    void 회원_가입_검증() {
        //given
        UserJoinForm userForm1 = new UserJoinForm();
        userForm1.setLoginId("");
        userForm1.setPassword("");
        userForm1.setNickName("nick");

        UserJoinForm userForm2 = new UserJoinForm();
        userForm2.setLoginId("test1234");
        userForm2.setPassword("Ps!!1234");
        userForm2.setNickName("에세이르");

        //when
        BindingResult bindingResult1 = Mockito.mock(BindingResult.class);
        BindingResult bindingResult2 = Mockito.mock(BindingResult.class);

        //then
        assertThatThrownBy(() -> validator.validate(userForm1, bindingResult1));    // 예외 터짐
        assertThatCode(() -> validator.validate(userForm2, bindingResult2));        // 예외 안터짐
    }

}
