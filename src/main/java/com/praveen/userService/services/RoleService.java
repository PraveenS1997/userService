package com.praveen.userService.services;

import com.praveen.userService.dtos.CreateRoleDto;
import com.praveen.userService.dtos.RoleDto;

public interface RoleService {
    RoleDto createRole(CreateRoleDto roleDto);
}
