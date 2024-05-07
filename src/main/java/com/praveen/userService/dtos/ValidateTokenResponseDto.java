package com.praveen.userService.dtos;

import com.praveen.userService.models.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidateTokenResponseDto {
    private SessionStatus status;
}
