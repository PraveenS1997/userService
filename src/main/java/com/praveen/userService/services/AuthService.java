package com.praveen.userService.services;

import com.praveen.userService.dtos.*;

public interface AuthService {
    UserDto signUp(SignUpRequestDto userDto);

    LoginResponseDto login(String email, String password);

    void logout(LogoutRequestDto logoutRequestDto);

    ValidateTokenResponseDto validateToken(ValidateTokenRequestDto validateTokenRequestDto);
}
