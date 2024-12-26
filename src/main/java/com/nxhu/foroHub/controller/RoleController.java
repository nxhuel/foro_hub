package com.nxhu.foroHub.controller;

import com.nxhu.foroHub.persistence.entity.PermissionEntity;
import com.nxhu.foroHub.persistence.entity.RoleEntity;
import com.nxhu.foroHub.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1")
@PreAuthorize("permitAll()")
public class RoleController
{
    @Autowired
    private RoleService roleService;

    @PostMapping("/create-role")
    public ResponseEntity<Void> createRole(@RequestBody RoleEntity role)
    {
        roleService.createRole(role);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<RoleEntity>> getRoles()
    {
        Set<RoleEntity> roleList = roleService.getRoles();
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @DeleteMapping("/delete-role/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId)
    {
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
