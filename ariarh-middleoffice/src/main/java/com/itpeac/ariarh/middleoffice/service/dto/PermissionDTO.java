package com.itpeac.ariarh.middleoffice.service.dto;

import com.itpeac.ariarh.middleoffice.domain.Permission;

public class PermissionDTO {

    private Long id;
    private String role;
    private String description;
    private String grouped;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGrouped() {
        return grouped;
    }

    public void setGrouped(String grouped) {
        this.grouped = grouped;
    }

    public PermissionDTO(Permission permission) {
        this.id = permission.getId();
        this.description = permission.getDescription();
        this.role = permission.getRole();
        this.grouped = permission.getGrouped();
    }


    public PermissionDTO() {

    }

    public Permission toPermission() {
        Permission permission = new Permission();
        permission.setId(id);
        permission.setDescription(description);
        permission.setRole(role);
        permission.setGrouped(grouped);
        return permission;
    }


}
