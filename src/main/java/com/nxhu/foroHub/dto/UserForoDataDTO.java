package com.nxhu.foroHub.dto;

import com.nxhu.foroHub.persistence.entity.ProfileEntity;
import com.nxhu.foroHub.persistence.entity.TopicEntity;

import java.util.Set;

public record UserForoDataDTO(
        String username,
        Set<ProfileEntity> list_profile,
        Set<TopicEntity> list_topic
)
{
}
