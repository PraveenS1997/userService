package com.praveen.userService.dtos;

import com.praveen.userService.models.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenResponseDto {
    private SessionStatus status;
    private String message;
}
