package com.praveen.userService.controllers;

import com.praveen.userService.dtos.*;
import com.praveen.userService.security.JwtTokenUtil;
import com.praveen.userService.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthService userService, JwtTokenUtil jwtTokenUtil) {
        this.authService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService
                .login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,
                jwtTokenUtil.getResponseCookie(loginResponseDto.getToken()).toString());

        return new ResponseEntity<>(loginResponseDto, headers, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        authService.logout(logoutRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UserDto user = authService.signUp(signUpRequestDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<ValidateTokenResponseDto> validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) {
        ValidateTokenResponseDto validateTokenResponseDto = authService.validateToken(validateTokenRequestDto);
        return new ResponseEntity<>(validateTokenResponseDto, HttpStatus.OK);
    }
}
