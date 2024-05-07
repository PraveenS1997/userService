package com.praveen.userService.dtos;

import com.praveen.userService.models.Role;
import com.praveen.userService.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<RoleDto> roles;

    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        if(user.getRoles() == null || user.getRoles().isEmpty()){
            userDto.setRoles(new ArrayList<>());
            return userDto;
        }

        List<RoleDto> roles = new ArrayList<>();

        for(Role role : user.getRoles()){
            roles.add(new RoleDto(role.getRole()));
        }
        userDto.setRoles(roles);

        return userDto;
    }
}