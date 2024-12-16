package com.nxhu.foroHub.service;

import com.nxhu.foroHub.persistence.entity.UserEntity;

import java.util.Optional;
import java.util.Set;

public interface UserService
{
    void createUser(UserEntity aUser);

    Set<UserEntity> getUsers();

    Optional<UserEntity> getUserById(Long userId);

    void updateUser(Long userId, UserEntity newUser);

    void deleteUserById(Long userId);
}
