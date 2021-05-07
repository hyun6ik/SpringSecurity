package com.example.security.service.impl;

import com.example.security.domain.entity.Role;
import com.example.security.domain.entity.RoleResources;
import com.example.security.repository.RoleRepository;
import com.example.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Role getRole(Long id) {
        return roleRepository.findById(id).orElse(new Role());
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
