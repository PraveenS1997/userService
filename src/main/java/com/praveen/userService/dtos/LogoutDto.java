package com.praveen.userService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutDto {
    private Long userId;
    private Long sessionId;
}
