package com.nxhu.foroHub.service;

import com.nxhu.foroHub.persistence.entity.AnswerEntity;

import java.util.Optional;
import java.util.Set;

public interface AnswerService
{
    void createAnswer(AnswerEntity aAnswer);

    Set<AnswerEntity> getAnswers();

    Optional<AnswerEntity> getAnswerById(Long answerId);

    void deleteAnswerById(Long answerId);
}
