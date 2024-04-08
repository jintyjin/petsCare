package com.petsCare.petsCare.user;

import com.petsCare.petsCare.user.dto.form.UserJoinForm;
import com.petsCare.petsCare.user.exception.UserDuplicatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userJoinForm", new UserJoinForm());
        return "/user/userJoinForm";
    }

    @PostMapping("/join")
    public String join(@Validated UserJoinForm userJoinForm, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "/user/userJoinForm";
        }

        try {
            userService.joinUser(userJoinForm);
        } catch (UserDuplicatedException e) {
            bindingResult.rejectValue("loginId", "user.duplicated", messageSource.getMessage(e.getMessage(), null, Locale.KOREAN));
            return "/user/userJoinForm";
        }

        return "redirect:/login";
    }
}
