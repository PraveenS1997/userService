package com.praveen.userService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(indexes = {@Index(name = "idx_role_name", columnList = "role", unique = true)})
public class Role extends BaseModel{
    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
