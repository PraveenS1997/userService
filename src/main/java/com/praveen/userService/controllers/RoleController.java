package com.praveen.userService.controllers;

import com.praveen.userService.dtos.AssignRolesToUserRequestDto;
import com.praveen.userService.dtos.CreateRoleDto;
import com.praveen.userService.dtos.RoleDto;
import com.praveen.userService.dtos.UserDto;
import com.praveen.userService.services.RoleService;
import com.praveen.userService.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody CreateRoleDto roleDto){
        RoleDto role = roleService.createRole(roleDto);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PostMapping("/users/{id}/roles")
    public ResponseEntity<UserDto> assignRolesToUser(
            @PathVariable("id") Long id,
            @RequestBody AssignRolesToUserRequestDto request) throws IllegalAccessException {
        UserDto user = userService.assignRolesToUser(id, request.getRoleIds());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
