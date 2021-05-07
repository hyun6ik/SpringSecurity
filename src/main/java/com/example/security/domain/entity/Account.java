package com.example.security.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String username;
    private String password;
    private String email;
    private String age;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountRole> accountRoles = new ArrayList<>();



    //연관관계 메소드
    public void addAccountRole(AccountRole accountRole){
        this.accountRoles.add(accountRole);
        accountRole.setAccount(this);
    }


    // 생성 메소드
    public static Account createAccount(Account account, AccountRole accountRole, Role role){
        accountRole.addRole(role);
        account.addAccountRole(accountRole);
        return account;
    }




    public void encodePassword(String password){
        this.password = password;
    }



}
