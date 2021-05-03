package com.example.security.service;

import com.example.security.domain.User;
import com.example.security.domain.UserDto;

public interface UserService {

    void createUser(User user);

    User dtoToEntity(UserDto userDto);
}
