package com.petsCare.petsCare.form.user;

import com.petsCare.petsCare.validation.ValidationGroups;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginForm {

    @Pattern(regexp = "^[0-9a-zA-Z]{5,16}$", groups = ValidationGroups.PatternGroup.class)
    private String loginId;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z[0-9]!@#$%^&*]{8,16}$", groups = ValidationGroups.PatternGroup.class)
    private String password;
}
