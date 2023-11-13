package com.example.Friends.domain;

import com.example.Friends.domain.enums.ProfileVisibility;
import com.example.Friends.domain.enums.Role;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"USER\"")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private LocalDateTime creationDate;

    private Role roles;

    @Enumerated(EnumType.STRING)
    private ProfileVisibility profileVisibility;

    @OneToMany(mappedBy = "sender")
    private Set<FriendRequest> friendRequestSent = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<FriendRequest> friendRequestReceived = new HashSet<>();
}
