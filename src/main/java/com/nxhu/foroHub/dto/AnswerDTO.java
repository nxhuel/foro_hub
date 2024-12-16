package com.nxhu.foroHub.dto;

import com.nxhu.foroHub.persistence.entity.TopicEntity;
import com.nxhu.foroHub.persistence.entity.UserEntity;

import java.time.LocalDateTime;

public record AnswerDTO(
        String message,
        TopicEntity topic,
        LocalDateTime creation_date,
        UserEntity author,
        String solution
)
{
}
