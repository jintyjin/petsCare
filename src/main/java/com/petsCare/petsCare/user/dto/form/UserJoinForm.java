package com.petsCare.petsCare.user.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserJoinForm {

    @NotBlank
    @Pattern(regexp = "^[0-9A-z]{5,16}$", message = "{validation.constraints.pattern.loginId}")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9A-z])(?=.*[0-9])(?=.*[!@#$%^&*])[0-9A-z[0-9]!@#$%^&*]{8,16}$"
            , message = "{validation.constraints.pattern.password}")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[가-힣]{2,4}$", message = "{validation.constraints.pattern.username}")
    private String username;

    public UserJoinForm(String loginId, String password, String username) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
    }
}

