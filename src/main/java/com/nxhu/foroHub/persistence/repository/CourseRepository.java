package com.nxhu.foroHub.persistence.repository;

import com.nxhu.foroHub.persistence.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>
{
}
