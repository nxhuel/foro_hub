package com.nxhu.foroHub.dto;


import java.time.LocalDateTime;

public record AnswerDTO(
        String message,
        String topic,
        LocalDateTime creation_date,
        String author,
        String solution
)
{
}
