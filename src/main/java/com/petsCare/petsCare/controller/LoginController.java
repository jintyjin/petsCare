package com.petsCare.petsCare.controller;

import com.petsCare.petsCare.form.user.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) boolean error,
                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        String errorMessage = (String) session.getAttribute("errorMessage");
        Integer errorCode = (Integer) session.getAttribute("errorCode");
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("error", error);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorCode", errorCode);
        return "/login/loginForm";
    }
}
