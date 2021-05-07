package com.example.security.domain.dto;

import com.example.security.domain.entity.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String username;
    private String password;
    private String email;
    private String age;
    private String role;
    private List<String> roleDtos = new ArrayList<>();

    public void addRoleDtos(List<String> roleDtos){
        this.setRoleDtos(roleDtos);
    }

}
