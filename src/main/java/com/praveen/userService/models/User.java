package com.praveen.userService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
// @Table annotation is used to specify the details of the table that this entity will be mapped to
@Table(indexes = {@Index(name = "idx_users_email", columnList = "email", unique = true)})
public class User extends BaseModel {
    private String name;

    private String email;

    private String password;

    // one user can have many sessions
    // mappedBy attribute is used to specify the entity property that owns the relationship
    // in this case, the user property in the Session entity
    // to avoid creating a join table, we use the mappedBy attribute
    @OneToMany(mappedBy = "user")
    private List<Session> sessions;
}
