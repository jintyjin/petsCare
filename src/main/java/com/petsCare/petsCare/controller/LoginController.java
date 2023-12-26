package com.petsCare.petsCare.controller;

import com.petsCare.petsCare.entity.session.SessionConst;
import com.petsCare.petsCare.entity.user.User;
import com.petsCare.petsCare.form.user.LoginForm;
import com.petsCare.petsCare.repository.user.UserRepository;
import com.petsCare.petsCare.validation.loginForm.LoginFormLoginValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginFormLoginValidator loginFormLoginValidator;
    private final UserRepository userRepository;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(loginFormLoginValidator);
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "/login/loginForm";
        }

        User user = userRepository.findByLoginId(loginForm.getLoginId()).get();

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, user);

        return "redirect:" + redirectURL;
    }
}
