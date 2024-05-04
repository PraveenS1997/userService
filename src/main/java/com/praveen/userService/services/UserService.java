package com.praveen.userService.services;

import com.praveen.userService.dtos.UserDto;
import com.praveen.userService.models.User;

public interface UserService {
    User addUser(UserDto userDto);

    boolean loginUser(UserDto userDto);

    boolean logoutUser(Long userId);
}
