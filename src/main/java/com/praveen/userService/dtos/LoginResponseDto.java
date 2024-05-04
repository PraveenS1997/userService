package com.praveen.userService.dtos;

import com.praveen.userService.models.Session;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String name;
    private String email;
    private String status;
    private String sessionId;
    private String token;

    public static LoginResponseDto from(Session session) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setName(session.getUser().getName());
        loginResponseDto.setEmail(session.getUser().getEmail());
        loginResponseDto.setStatus("Logged in successfully");
        loginResponseDto.setSessionId(session.getId().toString());
        loginResponseDto.setToken(session.getToken());

        return loginResponseDto;
    }
}
