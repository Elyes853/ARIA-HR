package com.itpeac.ariarh.middleoffice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itpeac.ariarh.middleoffice.domain.Permission;
import com.itpeac.ariarh.middleoffice.repository.jpa.PermissionRepository;
import com.itpeac.ariarh.middleoffice.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<Permission> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    @Override
    public Long countAllPermissions() {
        return permissionRepository.count();
    }

    @Override
    public Permission findOneById(Long id) {
        Optional<Permission> permission = permissionRepository.findOneById(id);

        return permission.isPresent() ? permission.get() : null;
    }

    @Override
    public List<String> findAllGroup() {
        return permissionRepository.findAllGroup();


    }

    @Override
    public List<Permission> findPermissionsByGroup(String group) {
        return permissionRepository.findPermissionByGroup(group);
    }


}
