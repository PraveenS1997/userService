package com.praveen.userService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
// @Table annotation is used to specify the details of the table that this entity will be mapped to
@Table(indexes = {@Index(name = "idx_users_email", columnList = "email", unique = true)})
@JsonSerialize(as = User.class)
public class User extends BaseModel {
    private String name;

    private String email;

    private String password;

    // one user can have many sessions
    // mappedBy attribute is used to specify the entity property that owns the relationship
    // in this case, the user property in the Session entity
    // to avoid creating a join table, we use the mappedBy attribute
    // @OneToMany(fetch = FetchType.LAZY)
    // @JsonIgnore
    // private List<Authorization> authorizations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();
}
