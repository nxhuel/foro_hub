package com.nxhu.foroHub.service.Impl;

import com.nxhu.foroHub.persistence.entity.TopicEntity;
import com.nxhu.foroHub.persistence.entity.UserEntity;
import com.nxhu.foroHub.persistence.repository.UserRepository;
import com.nxhu.foroHub.service.UserService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(UserEntity aUser)
    {
        try
        {
            userRepository.save(aUser);
        } catch (DuplicateRequestException e)
        {
            System.err.println("Error, user already exists: " + e.getMessage());
        }
    }

    @Override
    public Set<UserEntity> getUsers()
    {
        try
        {
            return new HashSet<>(userRepository.findAll());
        } catch (DataAccessException e)
        {
            System.err.println("Error getting themes: " + e.getMessage());
            return Collections.emptySet();
        }
    }

    @Override
    public Optional<UserEntity> getUserById(Long userId)
    {
        return userRepository.findById(userId);
    }

    @Override
    public void updateUser(Long userId, UserEntity newUser)
    {
        Optional<UserEntity> existingUser = this.getUserById(userId);
        if (existingUser.isPresent())
        {
            UserEntity updatedUser = existingUser.get();

            updatedUser.setUsername(newUser.getUsername());
            updatedUser.setEmail(newUser.getEmail());
            updatedUser.setPassword(newUser.getPassword());
            updatedUser.setList_profile(newUser.getList_profile());
            updatedUser.setList_topic(newUser.getList_topic());

            userRepository.save(updatedUser);
        } else
        {
            throw new EntityNotFoundException("User with ID " + userId + " no exists");
        }
    }

    @Override
    public void deleteUserById(Long userId)
    {
        if (this.getUserById(userId).isPresent())
        {
            userRepository.deleteById(userId);
        } else
        {
            throw new EntityNotFoundException("User with ID " + userId + " no exists");
        }
    }
}
