package com.petsCare.petsCare.validation.loginForm;

import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.form.user.LoginForm;
import com.petsCare.petsCare.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginFormLoginValidator extends LoginFormValidator {

    private final UserRepository userRepository;

    @Override
    public void validate(Object target, Errors errors) {
        LoginForm loginForm = (LoginForm) target;

        Optional<User> byLoginId = userRepository.findByLoginId(loginForm.getLoginId());

        byLoginId.ifPresentOrElse(
                user -> {
                    if (!BCrypt.checkpw(loginForm.getPassword(), user.getPassword())) {
                        errors.rejectValue("password", "wrongPassword");
                    }
                },
                () -> {
                    errors.rejectValue("loginId", "notExistMember");
                }
        );
    }
}