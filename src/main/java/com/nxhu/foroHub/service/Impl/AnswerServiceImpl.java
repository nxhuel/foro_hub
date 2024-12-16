package com.nxhu.foroHub.service.Impl;

import com.nxhu.foroHub.persistence.entity.AnswerEntity;
import com.nxhu.foroHub.persistence.repository.AnswerRepository;
import com.nxhu.foroHub.service.AnswerService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AnswerServiceImpl implements AnswerService
{
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void createAnswer(AnswerEntity aAnswer)
    {
        try
        {
            answerRepository.save(aAnswer);
        } catch (DuplicateRequestException e)
        {
            System.err.println("Error, answer already exists: " + e.getMessage());
        }
    }

    @Override
    public Set<AnswerEntity> getAnswers()
    {
        try
        {
            return new HashSet<>(answerRepository.findAll());
        } catch (DataAccessException e)
        {
            System.err.println("Error getting themes: " + e.getMessage());
            return Collections.emptySet();
        }
    }

    @Override
    public Optional<AnswerEntity> getAnswerById(Long answerId)
    {
        return answerRepository.findById(answerId);
    }

    @Override
    public void deleteAnswerById(Long answerId)
    {
        if (this.getAnswerById(answerId).isPresent())
        {
            answerRepository.deleteById(answerId);
        } else
        {
            throw new EntityNotFoundException("Topic with ID " + answerId + " no exists");
        }
    }
}
