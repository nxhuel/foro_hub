package com.nxhu.foroHub.service.Impl;

import com.nxhu.foroHub.persistence.entity.ProfileEntity;
import com.nxhu.foroHub.persistence.repository.ProfileRepository;
import com.nxhu.foroHub.service.ProfileService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService
{
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void createProfile(ProfileEntity aProfile)
    {
        try
        {
            profileRepository.save(aProfile);
        } catch (DuplicateRequestException e)
        {
            System.err.println("Error, profile already exists: " + e.getMessage());
        }
    }

    @Override
    public Set<ProfileEntity> getProfiles()
    {
        try
        {
            return new HashSet<>(profileRepository.findAll());
        } catch (DataAccessException e)
        {
            System.err.println("Error getting themes: " + e.getMessage());
            return Collections.emptySet();
        }
    }

    @Override
    public Optional<ProfileEntity> getProfileById(Long profileId)
    {
        return profileRepository.findById(profileId);
    }

    @Override
    public void updateProfile(Long profileId, ProfileEntity newProfile)
    {
        Optional<ProfileEntity> existingProfile = this.getProfileById(profileId);
        if (existingProfile.isPresent())
        {
            ProfileEntity updatedProfile = existingProfile.get();
            updatedProfile.setUsername(newProfile.getUsername());

            profileRepository.save(updatedProfile);
        } else
        {
            throw new EntityNotFoundException("Profile with ID " + profileId + " no exists");
        }
    }

    @Override
    public void deleteProfileById(Long profileId)
    {
        if (this.getProfileById(profileId).isPresent())
        {
            profileRepository.deleteById(profileId);
        } else
        {
            throw new EntityNotFoundException("Profile with ID " + profileId + " no exists");
        }
    }
}
