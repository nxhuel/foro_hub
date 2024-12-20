package com.nxhu.foroHub.dto;

import com.nxhu.foroHub.persistence.entity.Status;
import java.time.LocalDateTime;

public record TopicDTO(
        String title,
        String message,
        LocalDateTime creation_date,
        Status status,
        String author,
        String course
)
{
}
