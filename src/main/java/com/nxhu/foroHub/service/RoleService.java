package com.nxhu.foroHub.service;

import com.nxhu.foroHub.persistence.entity.RoleEntity;

import java.util.Set;

public interface RoleService
{
    void createRole(RoleEntity role);

    Set<RoleEntity> getRoles();

    void deleteRole(Long roleId);
}
