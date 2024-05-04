package com.praveen.userService.services;

import com.praveen.userService.dtos.UserDto;
import com.praveen.userService.exceptions.UserException;
import com.praveen.userService.models.User;
import com.praveen.userService.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UserDto userDto) {
        User user = userRepository.findUserByEmail(userDto.getEmail());

        if(user != null){
            throw new UserException("Email " + userDto.getEmail() + " already exists");
        }

        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setName(userDto.getName());
        newUser.setPassword(userDto.getPassword());

        return userRepository.save(newUser);
    }

    @Override
    public boolean loginUser(UserDto userDto) {
        User user = userRepository.findUserByEmailAndPassword(userDto.getEmail(), userDto.getPassword());

        if(user == null){
            throw new UserException("Invalid username and password");
        }

        return true;
    }

    @Override
    public boolean logoutUser(Long userId) {
        return false;
    }
}
