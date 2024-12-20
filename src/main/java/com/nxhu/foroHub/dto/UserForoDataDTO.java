package com.nxhu.foroHub.dto;


import java.util.Set;

public record UserForoDataDTO(
        String username,
        Set<String> list_profile,
        Set<String> list_topic
)
{
}
