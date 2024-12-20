package com.nxhu.foroHub.dto;

import java.util.Set;

public record TopicAnswerDTO(
        String title,
        Set<String> list_answer
)
{
}
