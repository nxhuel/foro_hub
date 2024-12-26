package com.nxhu.foroHub.controller;

import com.nxhu.foroHub.dto.ProfileDTO;
import com.nxhu.foroHub.persistence.entity.ProfileEntity;
import com.nxhu.foroHub.service.ProfileService;
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
public class ProfileController
{
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create-profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> createProfile(@RequestBody ProfileEntity aProfile)
    {
        profileService.createProfile(aProfile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/profiles")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Set<ProfileDTO>> getProfiles()
    {
        boolean notFound = profileService.getProfiles().isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<ProfileDTO> profiles = profileService.getProfiles().stream()
                .map(p -> new ProfileDTO(p.getUsername()))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("/profile/{profileId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long profileId)
    {
        return profileService.getProfileById(profileId)
                .map(p -> new ProfileDTO(p.getUsername()))
                .map(profileDTO -> new ResponseEntity<>(profileDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update-profile/{profileId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> updateProfile(@PathVariable Long profileId, @RequestBody ProfileEntity newProfile)
    {
        boolean notFound = profileService.getProfileById(profileId).isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        profileService.updateProfile(profileId, newProfile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-profile/{profileId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long profileId)
    {
        boolean notFound = profileService.getProfileById(profileId).isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        profileService.deleteProfileById(profileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
