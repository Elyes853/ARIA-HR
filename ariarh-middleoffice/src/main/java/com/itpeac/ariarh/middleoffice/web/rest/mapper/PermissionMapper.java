package com.itpeac.ariarh.middleoffice.web.rest.mapper;

import com.itpeac.ariarh.middleoffice.domain.Permission;
import com.itpeac.ariarh.middleoffice.service.dto.PermissionDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionMapper implements Mapper<Permission, PermissionDTO> {

    @Override
    public PermissionDTO fromEntityToDto(Permission permission) {
        if (permission != null) {
            PermissionDTO permissionDTO = new PermissionDTO();
            permissionDTO.setId(permission.getId());
            permissionDTO.setDescription(permission.getDescription());
            permissionDTO.setGrouped(permission.getGrouped());
            permissionDTO.setRole(permission.getRole());

            return permissionDTO;
        }
        return null;
    }

    @Override
    public Permission fromDTOtoEntity(PermissionDTO permissionDTO) {
        if (permissionDTO != null) {
            Permission permission = new Permission();
            permission.setId(permissionDTO.getId());
            permission.setDescription(permissionDTO.getDescription());
            permission.setGrouped(permissionDTO.getGrouped());
            permission.setRole(permissionDTO.getRole());

            return permission;
        }
        return null;
    }

    public List<PermissionDTO> fromListEntityToListDto(List<Permission> permissions) {
        return permissions.stream().map(data -> fromEntityToDto(data)).collect(Collectors.toList());
    }

    public List<Permission> fromListDtoToEntity(List<PermissionDTO> permissionDTOS) {
        return permissionDTOS.stream().map(data -> fromDTOtoEntity(data)).collect(Collectors.toList());
    }
}
