package com.example.security.service.impl;

import com.example.security.domain.User;
import com.example.security.domain.UserDto;
import com.example.security.repository.UserRepository;
import com.example.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User dtoToEntity(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);
        user.encodePassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }
}
