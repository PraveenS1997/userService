package com.praveen.userService.models;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseModel {
    // primary key
    @Id
    // auto increment the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
