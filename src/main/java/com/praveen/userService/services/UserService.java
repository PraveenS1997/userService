package com.praveen.userService.services;

import com.praveen.userService.dtos.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUser(Long id);

    UserDto assignRolesToUser(Long id, List<Long> roleIds) throws IllegalAccessException;
}
