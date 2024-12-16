package com.nxhu.foroHub.controller;

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
@RequestMapping("/api/v1")
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
        Set<TopicDTO> topics = topicService.getTopics().stream()
                .map(t -> new TopicDTO(t.getTitle(), t.getMessage(), t.getCreation_date(), t.getStatus(), t.getAuthor(), t.getCourse()))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(topics, HttpStatus.OK);
    }
}
