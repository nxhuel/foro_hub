package com.nxhu.foroHub.service.Impl;

import com.nxhu.foroHub.persistence.entity.*;
import com.nxhu.foroHub.persistence.repository.CourseRepository;
import com.nxhu.foroHub.persistence.repository.TopicRepository;
import com.nxhu.foroHub.persistence.repository.UserRepository;
import com.nxhu.foroHub.service.TopicService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void createTopic(TopicEntity aTopic)
    {
        UserEntity user = userRepository.findById(aTopic.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CourseEntity course = courseRepository.findById(aTopic.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        aTopic.setAuthor(user);
        aTopic.setCourse(course);
        try
        {
            topicRepository.save(aTopic);
        } catch (DuplicateRequestException e)
        {
            System.err.println("Error, topic already exists: " + e.getMessage());
        }
    }

    @Override
    @Cacheable("topics")
    public Set<TopicEntity> getTopics()
    {
        try
        {
            return new HashSet<>(topicRepository.findAll());
        } catch (DataAccessException e)
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
            TopicEntity updatedTopic = existingTopic.get();

            updatedTopic.setTitle(newTopic.getTitle());
            updatedTopic.setMessage(newTopic.getMessage());
            updatedTopic.setCreation_date(newTopic.getCreation_date());
            updatedTopic.setStatus(newTopic.getStatus());
            updatedTopic.setCourse(newTopic.getCourse());

            topicRepository.save(updatedTopic);
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
