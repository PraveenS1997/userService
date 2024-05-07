package com.praveen.userService.controllers;

import com.praveen.userService.dtos.CreateRoleDto;
import com.praveen.userService.dtos.RoleDto;
import com.praveen.userService.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody CreateRoleDto roleDto){
        RoleDto role = roleService.createRole(roleDto);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }
}
