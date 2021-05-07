package com.example.security.service;

import com.example.security.domain.entity.Resources;

import java.util.List;

public interface ResourcesService {

    Resources getResources(Long id);

    List<Resources> getResources();

    void createResources(Resources resources);

    void deleteResources(Long id);
}
