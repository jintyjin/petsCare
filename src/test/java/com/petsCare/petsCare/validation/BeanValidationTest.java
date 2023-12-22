package com.petsCare.petsCare.validation;

import com.petsCare.petsCare.form.user.UserJoinForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BeanValidationTest {

    @Test
    void 회원_가입_검증() {
        //given
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UserJoinForm userForm1 = new UserJoinForm();
        userForm1.setLoginId("");
        userForm1.setPassword("");
        userForm1.setNickName("nick");

        UserJoinForm userForm2 = new UserJoinForm();
        userForm2.setLoginId("test1234");
        userForm2.setPassword("Ps!!1234");
        userForm2.setNickName("에세이르");

        //when
        Set<ConstraintViolation<UserJoinForm>> validations1 = validator.validate(userForm1);
        Set<ConstraintViolation<UserJoinForm>> validations2 = validator.validate(userForm2);

        //then
        assertFalse(validations1.isEmpty());
        assertTrue(validations2.isEmpty());
    }
}
