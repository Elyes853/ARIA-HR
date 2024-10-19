package com.itpeac.ariarh.middleoffice.web.rest;

import com.itpeac.ariarh.middleoffice.domain.Permission;
import com.itpeac.ariarh.middleoffice.service.PermissionService;
import com.itpeac.ariarh.middleoffice.service.dto.PageRequest;
import com.itpeac.ariarh.middleoffice.service.dto.PageResponse;
import com.itpeac.ariarh.middleoffice.service.dto.PermissionDTO;
import com.itpeac.ariarh.middleoffice.web.rest.mapper.PermissionMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "/permissions", description = "permissions")
public class PermissionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    @Inject
    private PermissionService permissionService;
    @Autowired
    private PermissionMapper permissionMapper;


    @ApiOperation(value = "Retourner la list des permissions en mode lazy", response = PageResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "KO !!!")

    })
    @PostMapping(value = "/permissions", headers = "X-action=search")
    public PageResponse<PermissionDTO> getAllPermissions(@RequestBody PageRequest pageRequest) {
        MDC.put("action", "GET_PERMISSIONS");
        LOGGER.info("");
        final Long countTotal = permissionService.countAllPermissions();
        final Page<Permission> page = permissionService.getAllPermissions(pageRequest.toPageable());
        PageResponse<PermissionDTO> response = new PageResponse<PermissionDTO>(
                ((countTotal) / pageRequest.lazyLoadEvent.rows) + (countTotal % pageRequest.lazyLoadEvent.rows),
                countTotal, permissionMapper.fromListEntityToListDto(page.getContent()));
        return response;

    }

    @GetMapping("/permissions/{id}")
    @ApiOperation(value = "Requêter une permission par un id unique")
    public ResponseEntity<PermissionDTO> getPermission(@PathVariable Long id) {
        MDC.put("action", "GET_PERMISSION_BY_ID");
        LOGGER.info("idPermission {}", id);

        PermissionDTO res = permissionMapper.fromEntityToDto(permissionService.findOneById(id));
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }

    @GetMapping("/permissionsgroup")
    @ApiOperation("requeter les groupes des permissions")
    public ResponseEntity<List<String>> getAllPermissionsGroup() {
        MDC.put("action", "GET_PERMISSION_GROUPS");
        LOGGER.info("");
        List<String> res = permissionService.findAllGroup();
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }

    @GetMapping("/permissionbygroup")
    public ResponseEntity<List<PermissionDTO>> getPermissionByGroup(@RequestParam String group) {
        MDC.put("action", "GET_PERMISSIONS_BY_GROUP");
        LOGGER.info("group {}", group);

        List<PermissionDTO> res = permissionMapper.fromListEntityToListDto(permissionService.findPermissionsByGroup(group));
        return res != null ? ResponseEntity.ok(res) : ResponseEntity.notFound().build();
    }


}
