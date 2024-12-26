package com.nxhu.foroHub.controller;

import com.nxhu.foroHub.controller.dto.AuthCreateUserRequest;
import com.nxhu.foroHub.controller.dto.AuthLoginRequest;
import com.nxhu.foroHub.controller.dto.AuthResponse;
import com.nxhu.foroHub.service.Impl.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController
{
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser)
    {
        return new ResponseEntity<>(this.userDetailService.createUser(authCreateUser), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest)
    {
        return new ResponseEntity<AuthResponse>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }
}
