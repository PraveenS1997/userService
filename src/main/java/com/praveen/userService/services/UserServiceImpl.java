package com.praveen.userService.services;

import com.praveen.userService.dtos.UserDto;
import com.praveen.userService.exceptions.RoleNotFoundException;
import com.praveen.userService.exceptions.UserNotFoundException;
import com.praveen.userService.models.Role;
import com.praveen.userService.models.User;
import com.praveen.userService.repositories.RoleRepository;
import com.praveen.userService.repositories.UserRepository;
import com.praveen.userService.security.JwtTokenUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User with " + id + " not found");
        }

        return UserDto.from(optionalUser.get());
    }

    @Override
    public UserDto assignRolesToUser(Long id, List<Long> roleIds, String token) throws IllegalAccessException {
        EnsureUserHasAdminRole(token);

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User with " + id + " not found");
        }

        User user = optionalUser.get();
        List<Role> roles = new ArrayList<>();

        for(Long roleId : roleIds){
            Optional<Role> optionalRole = roleRepository.findById(roleId);

            if(optionalRole.isEmpty()){
                throw new RoleNotFoundException("Role with " + roleId + " not found");
            }

            roles.add(optionalRole.get());
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }

    private void EnsureUserHasAdminRole(String token) throws IllegalAccessException {
        if(!jwtTokenUtil.isAdminUser(token)){
            throw new IllegalAccessException("User does not have permission to assign roles to other users");
        }
    }
}
