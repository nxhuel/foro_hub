package com.nxhu.foroHub.service;

import com.nxhu.foroHub.persistence.entity.TopicEntity;

import java.util.Optional;
import java.util.Set;

public interface TopicService
{
    void createTopic(TopicEntity aTopic);

    Set<TopicEntity> getTopics();

    Optional<TopicEntity> getTopicById(Long topicId);

    void updateTopic(Long topicId, TopicEntity newTopic);

    void deleteTopicById(Long topicId);
}
