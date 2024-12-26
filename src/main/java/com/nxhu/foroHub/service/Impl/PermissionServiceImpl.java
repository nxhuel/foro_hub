package com.nxhu.foroHub.service.Impl;

import com.nxhu.foroHub.persistence.entity.PermissionEntity;
import com.nxhu.foroHub.persistence.repository.PermissionRepository;
import com.nxhu.foroHub.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService
{
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void createPermission(PermissionEntity permission)
    {
        permissionRepository.save(permission);
    }

    @Override
    public Set<PermissionEntity> getPermissions()
    {
        try
        {
            return new HashSet<>(permissionRepository.findAll());
        } catch (DataAccessException e)
        {
            System.err.println("Error getting permissions: " + e.getMessage());
            return Collections.emptySet();
        }
    }

    @Override
    public void deletePermission(Long permissionId)
    {
        boolean found = permissionRepository.findById(permissionId).isPresent();
        if (found)
        {
            permissionRepository.deleteById(permissionId);
        }
    }
}
