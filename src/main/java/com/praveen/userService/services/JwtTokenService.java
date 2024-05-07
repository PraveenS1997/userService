package com.praveen.userService.services;

import com.praveen.userService.models.User;

public interface JwtTokenService {
    String generateAuthToken(User user);

    boolean isTokenExpired(String token);
}
