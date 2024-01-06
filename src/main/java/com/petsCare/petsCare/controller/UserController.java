package com.petsCare.petsCare.controller;

import com.petsCare.petsCare.exception.DuplicatedLoginIdException;
import com.petsCare.petsCare.exception.DuplicatedNickNameException;
import com.petsCare.petsCare.form.user.UserJoinForm;
import com.petsCare.petsCare.service.user.UserService;
import com.petsCare.petsCare.validation.ValidationSequence;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userJoinForm", new UserJoinForm());
        return "/user/userJoinForm";
    }

    @PostMapping("/join")
    public String join(@Validated(ValidationSequence.class) UserJoinForm userJoinForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/user/userJoinForm";
        }

        try {
            userService.joinUser(userJoinForm);
        } catch (DuplicatedLoginIdException e) {
            FieldError fieldError = new FieldError("userJoinForm", "loginId", e.getMessage());
            bindingResult.addError(fieldError);
            return "/user/userJoinForm";
        } catch (DuplicatedNickNameException e) {
            FieldError fieldError = new FieldError("userJoinForm", "nickName", e.getMessage());
            bindingResult.addError(fieldError);
            return "/user/userJoinForm";
        }

        return "redirect:/login";
    }
}
