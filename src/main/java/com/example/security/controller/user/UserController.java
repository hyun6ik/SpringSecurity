package com.example.security.controller.user;

import com.example.security.domain.User;
import com.example.security.domain.UserDto;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @PostMapping("/users")
    public String createUser(UserDto userDto){

        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);
        User user = userService.dtoToEntity(userDto);
        userService.createUser(user);


        return "redirect:/";
    }
}
