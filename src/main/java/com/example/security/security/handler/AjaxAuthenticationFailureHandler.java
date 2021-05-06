package com.example.security.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "아이디 또는 패스워드가 유효하지 않는데요? ㅋ_ㅋ";

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);

        if(exception instanceof BadCredentialsException){
            errorMessage = "비밀번호 틀렸는데요? ㅋ_ㅋ";
        } else if (exception instanceof InsufficientAuthenticationException){
            errorMessage = "시크릿키 안맞는데요? ㅋ_ㅋ";
        }

        objectMapper.writeValue(response.getWriter(), errorMessage);
    }
}
