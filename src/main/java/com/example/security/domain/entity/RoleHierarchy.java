package com.example.security.domain.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "ROLE_HIERARCHY")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RoleHierarchy implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_hierarchy_id")
    private Long id;

    @Column(name = "child_name")
    private String childName;

    @ManyToOne(cascade = ALL, fetch = LAZY)
    @JoinColumn(name = "parent_name", referencedColumnName = "child_name")
    private RoleHierarchy parentName;

    @OneToMany(mappedBy = "parentName", cascade = ALL)
    private List<RoleHierarchy> roleHierarchies = new ArrayList<>();


}
