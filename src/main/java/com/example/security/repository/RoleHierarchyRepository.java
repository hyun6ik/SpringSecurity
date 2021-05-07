package com.example.security.repository;

import com.example.security.domain.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy,Long> {

    RoleHierarchy findByChildName(String roleName);
}
