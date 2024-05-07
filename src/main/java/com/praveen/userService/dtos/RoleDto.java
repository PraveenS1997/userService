package com.praveen.userService.dtos;

import com.praveen.userService.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleDto {
    private String role;

    public static RoleDto from(Role role){
        return new RoleDto(role.getRole());
    }
}
