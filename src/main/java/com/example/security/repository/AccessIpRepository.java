package com.example.security.repository;

import com.example.security.domain.entity.AccessIp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessIpRepository extends JpaRepository<AccessIp, Long> {

    AccessIp findByIpAddress(String IpAddress);
}

