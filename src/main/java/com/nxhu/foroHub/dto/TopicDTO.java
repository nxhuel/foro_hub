package com.nxhu.foroHub.dto;

import com.nxhu.foroHub.persistence.entity.CourseEntity;
import com.nxhu.foroHub.persistence.entity.Status;
import com.nxhu.foroHub.persistence.entity.UserEntity;

import java.time.LocalDateTime;

public record TopicDTO(
        String title,
        String message,
        LocalDateTime creation_date,
        Status status,
        UserEntity author,
        CourseEntity course
)
{
}
