package com.example.security.service.impl.utils;

import com.example.security.domain.dto.AccountDto;
import com.example.security.domain.entity.Account;
import com.example.security.domain.entity.AccountRole;
import com.example.security.domain.entity.Role;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceUtils {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public Account dtoToAccount(AccountDto accountDto){
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDto, Account.class);
        account.encodePassword(passwordEncoder.encode(accountDto.getPassword()));

        if(account.getAccountRoles() != null){
            List<Role> roles = accountDto.getRoleDtos().stream().map(r ->
                    new Role()).collect(Collectors.toList());
            for (Role role : roles) {
                AccountRole accountRole = new AccountRole();
                accountRole.addRole(role);
                account.addAccountRole(accountRole);
            }
        }
        return account;
    }

    @Transactional
    public AccountDto AccountToDto(Account account){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(account, AccountDto.class);
    }

    @Transactional
    public List<String> StreamRoles(Account account){
        List<AccountRole> accountRoles = account.getAccountRoles();
        ArrayList<String> list = new ArrayList<>();
        for (AccountRole accountRole : accountRoles) {
            list.add(accountRole.getRole().getRoleName());
        }
        return list;
    }

}
