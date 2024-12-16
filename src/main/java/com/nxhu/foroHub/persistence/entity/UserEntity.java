package com.nxhu.foroHub.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    @ManyToMany
    @JoinTable(name = "user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<ProfileEntity> list_profile = new HashSet<>();
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<TopicEntity> list_topic = new HashSet<>();
}
