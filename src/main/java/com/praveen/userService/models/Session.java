package com.praveen.userService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Session extends BaseModel{
    private String token;

    // persist the enum as an integer in the database instead of a string
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus status;

    // many sessions can be associated with one user
    @ManyToOne
    private User user;
}
