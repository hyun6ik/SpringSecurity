package com.example.security.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MessageController {

    @GetMapping("/messages")
    public String mypage() throws Exception{
        return "user/messages";
    }

    @GetMapping("/api/messages")
    @ResponseBody
    public String apiMessage(){
        return "messages OK";
    }
}
