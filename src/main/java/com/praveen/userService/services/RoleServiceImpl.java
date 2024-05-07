package com.praveen.userService.services;

import com.praveen.userService.dtos.CreateRoleDto;
import com.praveen.userService.dtos.RoleDto;
import com.praveen.userService.models.Role;
import com.praveen.userService.repositories.RoleRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(CreateRoleDto roleDto) {
        Role role = new Role();
        role.setRole(roleDto.getRole());

        Role savedRole = roleRepository.save(role);
        return RoleDto.from(savedRole);
    }
}
