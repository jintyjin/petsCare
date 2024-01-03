package com.petsCare.petsCare.controller;

import com.petsCare.petsCare.form.user.UserJoinForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("userJoinForm", new UserJoinForm());
        return "/user/joinForm";
    }
}
