package com.praveen.userService.controllers;

import com.praveen.userService.dtos.AssignRolesToUserRequestDto;
import com.praveen.userService.dtos.UserDto;
import com.praveen.userService.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id){
        UserDto user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> assignRolesToUser(
            @CookieValue(name = "auth-token") String authToken,
            @PathVariable("id") Long id,
            @RequestBody AssignRolesToUserRequestDto request){
        UserDto user = userService.assignRolesToUser(id, request.getRoleIds());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
