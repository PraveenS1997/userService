package com.praveen.userService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session {
    @Id
    private Long sessionId;

    private String token;

    private boolean isValid;

    @ManyToOne
    private User user;
}
