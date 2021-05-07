package com.example.security.domain.dto;

import com.example.security.domain.entity.Role;
import com.example.security.domain.entity.RoleResources;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesDto {

    private String id;
    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    private String roleName;
    private List<RoleDto> roleDtos = new ArrayList<>();
}
