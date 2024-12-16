package com.nxhu.foroHub.persistence.repository;

import com.nxhu.foroHub.persistence.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long>
{
}
