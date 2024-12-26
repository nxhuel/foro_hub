package com.nxhu.foroHub.service.Impl;

import com.nxhu.foroHub.controller.dto.AuthLoginRequest;
import com.nxhu.foroHub.controller.dto.AuthResponse;
import com.nxhu.foroHub.persistence.entity.UserEntity;
import com.nxhu.foroHub.persistence.repository.UserRepository;
import com.nxhu.foroHub.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserEntity userEntity = userRepository.findUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user " + username + " not exist."));

        // Verificar si el usuario está habilitado
        if (!userEntity.isEnabled())
        {
            throw new UsernameNotFoundException("The user " + username + " not enable.");
        }

        // Convierte todo en un objeto que entiende spring security
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Toma los roles del usuario
        userEntity.getRoleList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        // Toma los permisos  de ese rol
        userEntity.getRoleList().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest)
    {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username, "User loged succesfuly", accessToken, true);
        return authResponse;
    }

    public Authentication authenticate(String username, String password)
    {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null)
        {
            throw new BadCredentialsException("Invalid username or password.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
