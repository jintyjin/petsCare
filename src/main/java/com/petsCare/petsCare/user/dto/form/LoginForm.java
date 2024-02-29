package com.petsCare.petsCare.user.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginForm {


    @NotBlank
    @Pattern(regexp = "^[0-9A-z]{5,16}$", message = "{validation.constraints.pattern.loginId}")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-z[0-9]!@#$%^&*]{8,16}$"
            , message = "{validation.constraints.pattern.password}")
    private String password;
}
