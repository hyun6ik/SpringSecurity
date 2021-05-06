package com.example.security.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "아이디 또는 패스워드가 일치하지 않는데요? ㅋ_ㅋ";

        if(exception instanceof BadCredentialsException){
            errorMessage = "비밀번호 틀렸는데요? ㅋ_ㅋ";
        } else if (exception instanceof InsufficientAuthenticationException){
            errorMessage = "시크릿키 안맞는데요? ㅋ_ㅋ";
        }

        setDefaultFailureUrl("/login?error=true&exception=" + exception.getMessage());
        super.onAuthenticationFailure(request,response,exception);

    }
}
