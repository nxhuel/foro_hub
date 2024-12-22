package com.nxhu.foroHub.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    @Email(regexp = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    private String email;
    @Column(nullable = false, unique = true)
    private String password;

    // Atributos de spring security
    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "account_no_expired")
    private boolean accountNoExpired;
    @Column(name = "account_no_locked")
    private boolean accountNoLocked;
    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roleList = new HashSet<>();

    // Atributos extras
    @OneToMany
    @JoinTable(name = "user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<ProfileEntity> list_profile = new HashSet<>();
    @OneToMany
    @JoinTable(name = "user_topic",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<TopicEntity> list_topic = new HashSet<>();
}
