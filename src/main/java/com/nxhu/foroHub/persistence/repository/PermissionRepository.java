package com.nxhu.foroHub.persistence.repository;

import com.nxhu.foroHub.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long>
{
}
