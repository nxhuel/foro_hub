package com.nxhu.foroHub.persistence.repository;

import com.nxhu.foroHub.persistence.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long>
{
}
