package com.example.security.security.listener;

import com.example.security.domain.entity.*;
import com.example.security.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ResourcesRepository resourcesRepository;
    private final RoleHierarchyRepository roleHierarchyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessIpRepository accessIpRepository;
    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        setupSecurityResources();

        setupAccessIpData();

        alreadySetup = true;
    }

    private void setupSecurityResources() {

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        Role managerRole = createRoleIfNotFound("ROLE_MANAGER", "매니저권한");
        Role userRole = createRoleIfNotFound("ROLE_USER", "사용자권한");

        Resources adminResource = createResourceIfNotFound("/admin/**", "", adminRole, "url");
        createResourceIfNotFound("execution(public * io.security.corespringsecurity.aopsecurity.*Service.pointcut*(..))", "", adminRole, "pointcut");

        createUserIfNotFound("admin", "admin@admin.com", "pass", adminRole);

        createRoleHierarchyIfNotFound(managerRole, adminRole);
        createRoleHierarchyIfNotFound(userRole, managerRole);
    }

    @Transactional
    public Role createRoleIfNotFound(String roleName, String roleDesc) {

        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            role = Role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
        }
        return roleRepository.save(role);
    }

    @Transactional
    public Account createUserIfNotFound(final String userName, final String email, final String password, Role role) {

        Account account = userRepository.findByUsername(userName);
        AccountRole accountRole = new AccountRole();

        if (account == null) {
            account = Account.builder()
                    .username(userName)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .build();

        }
        accountRole.addRole(role);
        account.addAccountRole(accountRole);
        return userRepository.save(account);
    }

    @Transactional
    public Resources createResourceIfNotFound(String resourceName, String httpMethod, Role role, String resourceType) {
        Resources resources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);
        if (resources == null) {
            resources= Resources.builder()
                    .resourceName(resourceName)
                    .httpMethod(httpMethod)
                    .resourceType(resourceType)
                    .orderNum(count.incrementAndGet())
                    .build();
        }
        RoleResources roleResources = RoleResources.createRoleResources(role);
        resources.addRoleResources(roleResources);
        return resourcesRepository.save(resources);
    }

    @Transactional
    public void createRoleHierarchyIfNotFound(Role childRole, Role parentRole) {

        RoleHierarchy roleHierarchy = roleHierarchyRepository.findByChildName(parentRole.getRoleName());
        if (roleHierarchy == null) {
            roleHierarchy = RoleHierarchy.builder()
                    .childName(parentRole.getRoleName())
                    .build();
        }
        RoleHierarchy parentRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);

        roleHierarchy = roleHierarchyRepository.findByChildName(childRole.getRoleName());
        if (roleHierarchy == null) {
            roleHierarchy = RoleHierarchy.builder()
                    .childName(childRole.getRoleName())
                    .build();
        }

        RoleHierarchy childRoleHierarchy = roleHierarchyRepository.save(roleHierarchy);
        childRoleHierarchy.setParentName(parentRoleHierarchy);
    }

    private void setupAccessIpData() {

        AccessIp byIpAddress = accessIpRepository.findByIpAddress("0:0:0:0:0:0:0:1");
        if (byIpAddress == null) {
            AccessIp accessIp = AccessIp.builder()
                    .ipAddress("0:0:0:0:0:0:0:1")
                    .build();
            accessIpRepository.save(accessIp);
        }
    }
}
