package com.nxhu.foroHub.controller;

import com.nxhu.foroHub.dto.UserForoDataDTO;
import com.nxhu.foroHub.dto.UserPersonalDataDTO;
import com.nxhu.foroHub.persistence.entity.UserEntity;
import com.nxhu.foroHub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<Void> createTopic(@RequestBody UserEntity aUser)
    {
        userService.createUser(aUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users-personal-data")
    public ResponseEntity<Set<UserPersonalDataDTO>> getUsersPersonalData()
    {
        boolean notFound = userService.getUsers().isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<UserPersonalDataDTO> usersPersonalData = userService.getUsers().stream()
                .map(u -> new UserPersonalDataDTO(u.getUsername(), u.getEmail(), u.getPassword()))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(usersPersonalData, HttpStatus.OK);
    }

    @GetMapping("/users-foro-data")
    public ResponseEntity<Set<UserForoDataDTO>> getUsersForoData()
    {
        boolean notFound = userService.getUsers().isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<UserForoDataDTO> usersForoData = userService.getUsers().stream()
                .map(u -> new UserForoDataDTO(u.getUsername(), u.getList_profile(), u.getList_topic()))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(usersForoData, HttpStatus.OK);
    }

    @GetMapping("/user-personal-data/{id}")
    public ResponseEntity<UserPersonalDataDTO> getUserPersonalData(@PathVariable Long userId)
    {
        return userService.getUserById(userId)
                .map(u -> new UserPersonalDataDTO(u.getUsername(), u.getEmail(), u.getPassword()))
                .map(userPersonalDataDTO -> new ResponseEntity<>(userPersonalDataDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user-foro-data/{id}")
    public ResponseEntity<UserForoDataDTO> getUserForoData(@PathVariable Long userId)
    {
        return userService.getUserById(userId)
                .map(u -> new UserForoDataDTO(u.getUsername(), u.getList_profile(), u.getList_topic()))
                .map(userForoDataDTO -> new ResponseEntity<>(userForoDataDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody UserEntity newUser)
    {
        boolean notFound = userService.getUserById(userId).isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.updateUser(userId, newUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId)
    {
        boolean notFound = userService.getUserById(userId).isEmpty();
        if (notFound)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
