package com.nxhu.foroHub.persistence.repository;

import com.nxhu.foroHub.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>
{
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
