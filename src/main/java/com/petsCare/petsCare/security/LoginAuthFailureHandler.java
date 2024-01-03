package com.petsCare.petsCare.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
@Slf4j
public class LoginAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        int errorCode = 0;

        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "wrongPassword.loginForm.loginIdAndPassword";
            errorCode = 1;
        } else {
            errorMessage = "error.loginForm";
            errorCode = 2;
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        HttpSession session = request.getSession();
        session.setAttribute("errorMessage", errorMessage);
        session.setAttribute("errorCode", errorCode);
        setDefaultFailureUrl("/login?error=true");
        log.info("LoginAuthFailureHandler");
        super.onAuthenticationFailure(request, response, exception);
    }
}
