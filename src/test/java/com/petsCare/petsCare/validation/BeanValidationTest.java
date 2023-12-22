package com.petsCare.petsCare.validation;

import com.petsCare.petsCare.form.user.UserForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class BeanValidationTest {

    @Test
    void 회원_가입_검증() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UserForm userForm = new UserForm();
        userForm.setLoginId("");
        userForm.setPassword("");
        userForm.setNickName("nick");

        Set<ConstraintViolation<UserForm>> validations = validator.validate(userForm);
        for (ConstraintViolation<UserForm> validation : validations) {
            System.out.println("validation = " + validation);
            System.out.println("validation.getMessage() = " + validation.getMessage());
        }
    }
}
