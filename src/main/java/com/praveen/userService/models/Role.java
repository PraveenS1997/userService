package com.praveen.userService.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {@Index(name = "idx_role_name", columnList = "role", unique = true)})
@JsonSerialize(as = Role.class)
public class Role extends BaseModel{
    private String role;
}
