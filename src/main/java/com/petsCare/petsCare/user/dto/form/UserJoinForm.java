package com.petsCare.petsCare.user.dto.form;

import com.petsCare.petsCare.validation.ValidationGroups;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserJoinForm {

    @Pattern(regexp = "^[0-9a-zA-Z]{5,16}$", groups = ValidationGroups.PatternGroup.class)
    private String loginId;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z[0-9]!@#$%^&*]{8,16}$", groups = ValidationGroups.PatternGroup.class)
    private String password;

    @Pattern(regexp = "^[0-9a-zA-Z가-힣]{5,16}$", groups = ValidationGroups.PatternGroup.class)
    private String nickName;

    public UserJoinForm(String loginId, String password, String nickName) {
        this.loginId = loginId;
        this.password = password;
        this.nickName = nickName;
    }
}
