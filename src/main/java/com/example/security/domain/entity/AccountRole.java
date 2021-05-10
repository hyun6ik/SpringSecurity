package com.example.security.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRole implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_role_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    //연관 관계 메소드
    public void addRole(Role role){
        role.getAccountRoles().add(this);
        this.setRole(role);
    }

    //생성 메소드
    public static AccountRole createAccountRole(Role role){
        AccountRole accountRole = new AccountRole();
        accountRole.addRole(role);
        return accountRole;
    }


}
