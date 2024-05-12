package com.praveen.userService.services;

import com.praveen.userService.dtos.*;

public interface AuthService {
    UserDto signUp(SignUpRequestDto userDto);
}
