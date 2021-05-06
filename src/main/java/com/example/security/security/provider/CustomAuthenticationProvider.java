package com.example.security.security.provider;

import com.example.security.security.common.FormWebAuthenticationDetails;
import com.example.security.security.service.AccountContext;
import com.example.security.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.awt.datatransfer.StringSelection;


public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //검증을 위한 구현
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //현재 로그인할 때 입력한 username과 password
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();

        //user,password,authorities 들어있는 객체
        AccountContext accountContext = (AccountContext) customUserDetailsService.loadUserByUsername(username);

        //패스워드 검증 password와 accountText의 password 비교
        if(!passwordEncoder.matches(password, accountContext.getAccount().getPassword())){
            throw new BadCredentialsException("비밀번호 틀렸는데요? ㅋ_ㅋ");
        }

        FormWebAuthenticationDetails details = (FormWebAuthenticationDetails) authentication.getDetails();
        String secretKey = details.getSecretKey();
        if(secretKey == null || !"secret".equals(secretKey)){
            throw new InsufficientAuthenticationException("시크릿키 안맞아요 아니면 없어요ㅋ_ㅋ");
        }

        //인증이 성공했다면 UsernamePasswordAuthenticationToken에 username,password,권한정보 등록(인증객체 생성)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());

        //인증객체 전달
        return authenticationToken;
    }

    //현재 파라미터로 전달되는 authentication의 타입과 CustomAuthentication에서 사용하는 토큰이 일치하는지 확인
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
