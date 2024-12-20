package com.nxhu.foroHub.controller;

import com.nxhu.foroHub.dto.TopicAnswerDTO;
import com.nxhu.foroHub.dto.TopicDTO;
import com.nxhu.foroHub.persistence.entity.TopicEntity;
import com.nxhu.foroHub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class TopicController
{
    @Autowired
    private TopicService topicService;

    @PostMapping("/create-topic")
    public ResponseEntity<Void> createTopic(@RequestBody TopicEntity aTopic)
    {
        topicService.createTopic(aTopic);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/topics")
    public ResponseEntity<Set<TopicDTO>> getTopics()
    {
        boolean notFound = topicService.getTopics().isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<TopicDTO> topics = topicService.getTopics().stream()
                .map(t -> new TopicDTO(t.getTitle(), t.getMessage(), t.getCreation_date(), t.getStatus(), t.getAuthor().getUsername(), t.getCourse().getCategory()))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<TopicDTO> getTopic(@PathVariable Long topicId)
    {
        return topicService.getTopicById(topicId)
                .map(t -> new TopicDTO(t.getTitle(), t.getMessage(), t.getCreation_date(), t.getStatus(), t.getAuthor().getUsername(), t.getCourse().getCategory()))
                .map(topicDTO -> new ResponseEntity<>(topicDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/topic-answer/{topicId}")
    public ResponseEntity<TopicAnswerDTO> getTopicAnswer(@PathVariable Long topicId)
    {
        return topicService.getTopicById(topicId)
                .map(t -> new TopicAnswerDTO(
                        t.getTitle(),
                        t.getList_answer().stream().map(a -> a.getMessage()).collect(Collectors.toSet())))
                .map(topicAnswerDTO -> new ResponseEntity<>(topicAnswerDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update-topic/{topicId}")
    public ResponseEntity<Void> updateTopic(@PathVariable Long topicId, @RequestBody TopicEntity newTopic)
    {
        boolean notFound = topicService.getTopicById(topicId).isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        topicService.updateTopic(topicId, newTopic);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-topic/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topicId)
    {
        boolean notFound = topicService.getTopicById(topicId).isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        topicService.deleteTopicById(topicId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
