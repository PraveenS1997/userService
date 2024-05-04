package com.praveen.userService.controllers;

import com.praveen.userService.dtos.LogoutDto;
import com.praveen.userService.dtos.UserDto;
import com.praveen.userService.models.Session;
import com.praveen.userService.models.User;
import com.praveen.userService.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserDto addUser(@RequestBody UserDto userDto) {
        User user = userService.addUser(userDto);

        return UserDto.createFromUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) {
        Session session = userService.loginUser(userDto);
        return session.getToken();
    }

    @PostMapping("/logout")
    public String logout(@RequestBody LogoutDto logoutDto) {
        userService.logoutUser(logoutDto);
        return "Logged out successfully";
    }
}
