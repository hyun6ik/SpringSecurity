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
public class RoleResources implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_resources_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "resources_id")
    private Resources resources;


    // 연관 관계 메소드
    public void addResources(Resources resources){
        resources.getRoleResourcesList().add(this);
        this.setResources(resources);
    }

    public void addRole(Role role){
        this.role = role;
    }

    // 생성 메소드
    public static RoleResources createRoleResources(Role role){
        RoleResources roleResources = new RoleResources();
        roleResources.addRole(role);
        return roleResources;
    }
}
