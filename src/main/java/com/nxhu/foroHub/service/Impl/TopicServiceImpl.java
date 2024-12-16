package com.nxhu.foroHub.service.Impl;

import com.nxhu.foroHub.persistence.entity.*;
import com.nxhu.foroHub.persistence.repository.TopicRepository;
import com.nxhu.foroHub.service.TopicService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService
{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void createTopic(TopicEntity aTopic)
    {
        try
        {
            topicRepository.save(aTopic);
        } catch (DuplicateRequestException e)
        {
            System.err.println("Error, topic already exists: " + e.getMessage());
        }
    }

    @Override
    public Set<TopicEntity> getTopics()
    {
        try
        {
            return new HashSet<>(topicRepository.findAll());
        } catch (DataAccessException e) // Error con traer temas de la BD
        {
            System.err.println("Error getting themes: " + e.getMessage());
            return Collections.emptySet();
        }
    }

    @Override
    public Optional<TopicEntity> getTopicById(Long topicId)
    {
        return topicRepository.findById(topicId);
    }

    @Override
    public void updateTopic(Long topicId, TopicEntity newTopic)
    {
        Optional<TopicEntity> existingTopic = this.getTopicById(topicId);
        if (existingTopic.isPresent())
        {
            TopicEntity updatedTopic = existingTopic.get().builder()
                    .title(newTopic.getTitle())
                    .message(newTopic.getMessage())
                    .creation_date(newTopic.getCreation_date())
                    .status(newTopic.getStatus())
                    .course(newTopic.getCourse())
                    .build();
            this.createTopic(updatedTopic);
        } else
        {
            throw new EntityNotFoundException("Topic with ID " + topicId + " no exists");
        }
    }

    @Override
    public void deleteTopicById(Long topicId)
    {
        if (this.getTopicById(topicId).isPresent())
        {
            topicRepository.deleteById(topicId);
        } else
        {
            throw new EntityNotFoundException("Topic with ID " + topicId + " no exists");
        }
    }
}
