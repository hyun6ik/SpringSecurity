package com.example.security.domain.entity;

import lombok.*;
import org.springframework.stereotype.Controller;

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
public class Resources implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resources_id")
    private Long id;

    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;

    @OneToMany(mappedBy = "resources", cascade = CascadeType.ALL)
    private List<RoleResources> roleResourcesList = new ArrayList<>();

    // 연관 관계 메소드
    public void addRoleResources(RoleResources roleResources){
        roleResourcesList.add(roleResources);
        roleResources.setResources(this);
    }

    // 생성 메소드
    public static Resources createResources(Resources resources, RoleResources... roleResources){
        for (RoleResources roleResource : roleResources) {
            resources.addRoleResources(roleResource);
        }
        return resources;
    }


}
