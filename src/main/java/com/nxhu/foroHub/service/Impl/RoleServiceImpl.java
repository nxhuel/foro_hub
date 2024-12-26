package com.nxhu.foroHub.service.Impl;

import com.nxhu.foroHub.persistence.entity.RoleEntity;
import com.nxhu.foroHub.persistence.repository.RoleRepository;
import com.nxhu.foroHub.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void createRole(RoleEntity role)
    {
        roleRepository.save(role);
    }

    @Override
    public Set<RoleEntity> getRoles()
    {
        try
        {
            return new HashSet<>(roleRepository.findAll());
        } catch (DataAccessException e)
        {
            System.err.println("Error getting roles: " + e.getMessage());
            return Collections.emptySet();
        }
    }

    @Override
    public void deleteRole(Long roleId)
    {
        boolean found = roleRepository.findById(roleId).isPresent();
        if (found)
        {
            roleRepository.deleteById(roleId);
        }
    }
}
