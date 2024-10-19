package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.domain.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PermissionService {

    Page<Permission> getAllPermissions(Pageable pageable);

    Long countAllPermissions();

    Permission findOneById(Long id);

    List<String> findAllGroup();

    List<Permission> findPermissionsByGroup(String group);

}
