package com.nxhu.foroHub.service;

import com.nxhu.foroHub.persistence.entity.PermissionEntity;

import java.util.Set;

public interface PermissionService
{
    void createPermission(PermissionEntity permission);

    Set<PermissionEntity> getPermissions();

    void deletePermission(Long permissionId);
}
