package com.example.security.controller.user;

import com.example.security.domain.entity.Account;
import com.example.security.domain.dto.AccountDto;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public String home() throws Exception{
        return "home";
    }

    @GetMapping("/mypage")
    public String myPage() throws Exception{
        return "user/mypage";
    }

    @GetMapping("/users")
    public String createUser(){
        return "user/login/register";
    }


}
