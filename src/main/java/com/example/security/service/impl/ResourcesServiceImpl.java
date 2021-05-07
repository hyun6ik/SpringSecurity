package com.example.security.service.impl;

import com.example.security.domain.entity.Resources;
import com.example.security.repository.ResourcesRepository;
import com.example.security.service.ResourcesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ResourcesServiceImpl implements ResourcesService {

    private ResourcesRepository resourcesRepository;

    @Transactional
    @Override
    public Resources getResources(Long id) {
        return resourcesRepository.findById(id).orElse(new Resources());
    }

    @Override
    public List<Resources> getResources() {
        return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Transactional
    @Override
    public void createResources(Resources resources) {
        resourcesRepository.save(resources);
    }

    @Transactional
    @Override
    public void deleteResources(Long id) {
        resourcesRepository.deleteById(id);
    }
}
