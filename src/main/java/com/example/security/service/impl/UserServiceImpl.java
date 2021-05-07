package com.example.security.service.impl;

import com.example.security.domain.entity.Account;
import com.example.security.domain.dto.AccountDto;
import com.example.security.domain.entity.AccountRole;
import com.example.security.domain.entity.Role;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import com.example.security.service.UserService;
import com.example.security.service.impl.utils.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserServiceUtils userServiceUtils;

    @Transactional
    @Override
    public void createUser(Account account) {
        Role role = roleRepository.findByRoleName("ROLE_USER");
        Account.createAccount(account, new AccountRole(), role);
        userRepository.save(account);
    }


    @Transactional
    @Override
    public void modifyUser(AccountDto accountDto) {
        Account account = userServiceUtils.dtoToAccount(accountDto);
        userRepository.save(account);
    }

    @Override
    public List<Account> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public AccountDto getUser(Long id) {
        Account account = userRepository.findById(id).orElse(new Account());
        AccountDto accountDto = userServiceUtils.AccountToDto(account);
        List<String> roles = userServiceUtils.StreamRoles(account);
        accountDto.addRoleDtos(roles);
        return accountDto;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Secured("ROLE_MANAGER")
    public void order() {
        System.out.println("order");
    }
}
