package com.praveen.userService.controllers;

import com.praveen.userService.dtos.*;
import com.praveen.userService.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// REST semantics (i.e. do not change state with the HTTP methods GET, HEAD, TRACE, OPTIONS).
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService userService) {
        this.authService = userService;
    }

    // Spring security provides a logout & logout endpoints by default,
    // so a custom endpoint is not necessary.

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UserDto user = authService.signUp(signUpRequestDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}