package com.example.security.security.configs;

import com.example.security.security.filter.AjaxLoginProcessingFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class AjaxLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, AjaxLoginConfigurer<H>, AjaxLoginProcessingFilter> {

    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private AuthenticationManager authenticationManager;

    public AjaxLoginConfigurer() {
        super(new AjaxLoginProcessingFilter(), null);
    }

    @Override
    public void init(H http) throws Exception {
        super.init(http);
    }

    @Override
    public void configure(H http) throws Exception {
       if(authenticationManager == null){
           // getSharedObject = 공유 객체 가져오는 저장소 개념
           authenticationManager = http.getSharedObject(AuthenticationManager.class);
       }
       // getAuthenticationFilter() = AjaxLoginProcessingFilter를 가져옴 -> 필터에 매니저 핸들러 설정
       getAuthenticationFilter().setAuthenticationManager(authenticationManager);
       getAuthenticationFilter().setAuthenticationSuccessHandler(successHandler);
       getAuthenticationFilter().setAuthenticationFailureHandler(failureHandler);

        // 인증 과정 처리
        // 세션 관련 처리
       SessionAuthenticationStrategy sessionAuthenticationStrategy = http.getSharedObject(SessionAuthenticationStrategy.class);
       if(sessionAuthenticationStrategy != null){
           getAuthenticationFilter().setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
       }
       // RememberMe 관련 처리
        RememberMeServices rememberMeServices = http.getSharedObject(RememberMeServices.class);
       if(rememberMeServices != null){
           getAuthenticationFilter().setRememberMeServices(rememberMeServices);
       }
       // 공유 객체에 AjaxLoginProcessingFilter 저장
       http.setSharedObject(AjaxLoginProcessingFilter.class, getAuthenticationFilter());
       // FilterBefore 설정
       http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    public AjaxLoginConfigurer<H> successHandlerAjax(AuthenticationSuccessHandler successHandler){
        this.successHandler = successHandler;
        return this;
    }

    public AjaxLoginConfigurer<H> failureHandlerAjax(AuthenticationFailureHandler failureHandler){
        this.failureHandler = failureHandler;
        return this;
    }

    public AjaxLoginConfigurer<H> setAuthenticationManager(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        return this;
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }
}
