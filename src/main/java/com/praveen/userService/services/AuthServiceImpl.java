package com.praveen.userService.services;

import com.praveen.userService.dtos.*;
import com.praveen.userService.exceptions.UserAlreadyExistException;
import com.praveen.userService.models.User;
import com.praveen.userService.repositories.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@Primary
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        Assert.notNull(signUpRequestDto, "SignUpRequestDto is required");
        Assert.notNull(signUpRequestDto.getEmail(), "Email is required");
        Assert.notNull(signUpRequestDto.getName(), "Name is required");
        Assert.notNull(signUpRequestDto.getPassword(), "Password is required");

        Optional<User> user = userRepository.findUserByEmail(signUpRequestDto.getEmail());

        if(user.isPresent()){
            throw new UserAlreadyExistException("Email " + signUpRequestDto.getEmail() + " already exists");
        }

        User newUser = new User();
        newUser.setEmail(signUpRequestDto.getEmail());
        newUser.setName(signUpRequestDto.getName());
        newUser.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));

        User savedUser = userRepository.save(newUser);
        return UserDto.from(savedUser);
    }
}