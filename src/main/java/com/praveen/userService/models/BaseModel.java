package com.praveen.userService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    // primary key
    @Id
    // auto increment the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
