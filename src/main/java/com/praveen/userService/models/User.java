package com.praveen.userService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(indexes = {@Index(name = "idx_users_email", columnList = "email", unique = true)})
public class User {
    @Id
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Session> sessions;
}
