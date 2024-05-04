package com.praveen.userService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequestDto {
    private Long userId;
    private Long sessionId;
    private String token;
}
