package com.praveen.userService.services;

import com.praveen.userService.dtos.LogoutDto;
import com.praveen.userService.dtos.UserDto;
import com.praveen.userService.models.Session;
import com.praveen.userService.models.User;

public interface UserService {
    User addUser(UserDto userDto);

    Session loginUser(UserDto userDto);

    void logoutUser(LogoutDto logoutDto);
}
