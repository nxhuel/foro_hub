package com.nxhu.foroHub.controller;

import com.nxhu.foroHub.persistence.entity.PermissionEntity;
import com.nxhu.foroHub.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1")
@PreAuthorize("permitAll()")
public class PermissionControler
{
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/create-permission")
    public ResponseEntity<Void> createPermission(@RequestBody PermissionEntity permission)
    {
        permissionService.createPermission(permission);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/permissions")
    public ResponseEntity<Set<PermissionEntity>> getPermissions()
    {
        Set<PermissionEntity> permissionList = permissionService.getPermissions();
        return new ResponseEntity<>(permissionList, HttpStatus.OK);
    }

    @DeleteMapping("/delete-permission/{permissionId}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long permissionId)
    {
        permissionService.deletePermission(permissionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
