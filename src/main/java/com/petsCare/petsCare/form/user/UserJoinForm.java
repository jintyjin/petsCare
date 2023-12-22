package com.petsCare.petsCare.form.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserJoinForm {

    @NotEmpty
    @Size(min = 8, max = 16)
    private String loginId;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z[0-9]!@#$%^&*]{8,16}$")
    private String password;

    @NotEmpty
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]{4,16}$")
    private String nickName;
}
