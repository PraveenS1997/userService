package com.praveen.userService.services;

import com.praveen.userService.dtos.LoginResponseDto;
import com.praveen.userService.dtos.LogoutRequestDto;
import com.praveen.userService.dtos.SignUpRequestDto;
import com.praveen.userService.dtos.UserDto;

public interface AuthService {
    UserDto signUp(SignUpRequestDto userDto);

    LoginResponseDto login(String email, String password);

    void logout(LogoutRequestDto logoutRequestDto);
}
