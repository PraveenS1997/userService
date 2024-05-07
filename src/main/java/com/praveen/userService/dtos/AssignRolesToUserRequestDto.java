package com.praveen.userService.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignRolesToUserRequestDto {
    private List<Long> roleIds;
}
