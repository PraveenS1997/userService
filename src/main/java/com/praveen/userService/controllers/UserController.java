package com.praveen.userService.controllers;

import com.praveen.userService.dtos.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @PostMapping("/users")
    public UserDto addUser(@RequestBody UserDto userDto) {
        return new UserDto();
    }

    @PostMapping("/login")
    public void login(@RequestBody UserDto userDto) {
        System.out.println("User signed up");
    }

    @PostMapping("/logout")
    public void logout() {
        System.out.println("User signed up");
    }
}
