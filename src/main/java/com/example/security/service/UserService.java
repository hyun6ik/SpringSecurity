package com.example.security.service;

import com.example.security.domain.Account;
import com.example.security.domain.AccountDto;

public interface UserService {

    void createUser(Account account);

    Account dtoToEntity(AccountDto accountDto);
}
