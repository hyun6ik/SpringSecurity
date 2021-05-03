package com.example.security.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("")
    public String home() throws Exception{
        return "home";
    }

    @GetMapping("/mypage")
    public String myPage() throws Exception{
        return "user/mypage";
    }
}
