package com.nxhu.foroHub.controller;

import com.nxhu.foroHub.dto.AnswerDTO;
import com.nxhu.foroHub.persistence.entity.AnswerEntity;
import com.nxhu.foroHub.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@PreAuthorize("denyAll()")
public class AnswerController
{
    @Autowired
    private AnswerService answerService;

    @PostMapping("/create-answer")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> createAnswer(@RequestBody AnswerEntity aAnswer)
    {
        answerService.createAnswer(aAnswer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/answers")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Set<AnswerDTO>> getAnswers()
    {
        boolean notFound = answerService.getAnswers().isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<AnswerDTO> answers = answerService.getAnswers().stream()
                .map(a -> new AnswerDTO(a.getMessage(), a.getTopic().getTitle(), a.getCreation_date(), a.getAuthor().getUsername(), a.getSolution()))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    @GetMapping("/answer/{answerId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AnswerDTO> getAnswer(@PathVariable Long answerId)
    {
        return answerService.getAnswerById(answerId)
                .map(a -> new AnswerDTO(a.getMessage(), a.getTopic().getTitle(), a.getCreation_date(), a.getAuthor().getUsername(), a.getSolution()))
                .map(answerDTO -> new ResponseEntity<>(answerDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete-answer/{answerId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long answerId)
    {
        boolean notFound = answerService.getAnswerById(answerId).isEmpty();
        if (notFound) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        answerService.deleteAnswerById(answerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
