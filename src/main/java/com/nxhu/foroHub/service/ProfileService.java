package com.nxhu.foroHub.service;


import com.nxhu.foroHub.persistence.entity.ProfileEntity;

import java.util.Optional;
import java.util.Set;

public interface ProfileService
{
    void createProfile(ProfileEntity aProfile);

    Set<ProfileEntity> getProfiles();

    Optional<ProfileEntity> getProfileById(Long profileId);

    void updateProfile(Long profileId, ProfileEntity newProfile);

    void deleteProfileById(Long profileId);
}
