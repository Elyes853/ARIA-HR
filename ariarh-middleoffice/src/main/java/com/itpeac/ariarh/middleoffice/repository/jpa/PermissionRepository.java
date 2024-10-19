package com.itpeac.ariarh.middleoffice.repository.jpa;

import com.itpeac.ariarh.middleoffice.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findOneById(Long id);

    @Query("select distinct p.grouped from Permission p order by p.grouped")
    List<String> findAllGroup();

    @Query("select p from Permission p where p.grouped=?1")
    List<Permission> findPermissionByGroup(String group);


}
