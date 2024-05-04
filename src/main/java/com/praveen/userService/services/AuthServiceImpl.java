package com.praveen.userService.services;

import com.praveen.userService.Utils.Guard;
import com.praveen.userService.dtos.*;
import com.praveen.userService.exceptions.UserException;
import com.praveen.userService.models.Session;
import com.praveen.userService.models.SessionStatus;
import com.praveen.userService.models.User;
import com.praveen.userService.repositories.SessionRepository;
import com.praveen.userService.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Primary
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           SessionRepository sessionRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        Guard.notNull(signUpRequestDto, "SignUpRequestDto is required");
        Guard.notEmpty(signUpRequestDto.getEmail(), "Email is required");
        Guard.notEmpty(signUpRequestDto.getName(), "Name is required");
        Guard.notEmpty(signUpRequestDto.getPassword(), "Password is required");

        User user = userRepository.findUserByEmail(signUpRequestDto.getEmail());

        if(user != null){
            throw new UserException("Email " + signUpRequestDto.getEmail() + " already exists");
        }

        User newUser = new User();
        newUser.setEmail(signUpRequestDto.getEmail());
        newUser.setName(signUpRequestDto.getName());
        newUser.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));

        User savedUser = userRepository.save(newUser);
        return UserDto.from(savedUser);
    }

    @Override
    public LoginResponseDto login(String email, String password) {
        Guard.notEmpty(email, "Email is required");
        Guard.notEmpty(password, "Password is required");

        User user = userRepository.findUserByEmail(email);

        if(user == null || !bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new UserException("Invalid username and password");
        }

        Session session = new Session();
        session.setToken(RandomStringUtils.randomAlphanumeric(30));
        session.setStatus(SessionStatus.ACTIVE);
        session.setExpiringAt(new Date(System.currentTimeMillis() + 3600000));
        session.setUser(user);
        sessionRepository.save(session);

        return LoginResponseDto.from(session);
    }

    @Override
    public void logout(LogoutRequestDto logoutRequestDto) {
        Guard.notNull(logoutRequestDto, "LoginRequestDto is required");
        Guard.notEmpty(logoutRequestDto.getToken(), "Token is required");
        Guard.greaterThanZero(logoutRequestDto.getUserId(), "User id is not valid");

        Session session = sessionRepository.findByToken(logoutRequestDto.getToken());

        // Todo: token validation will be done in the other method
        if(session == null){
            return;
        }

        session.setStatus(SessionStatus.INACTIVE);
        sessionRepository.save(session);
    }

    @Override
    public ValidateTokenResponseDto validateToken(ValidateTokenRequestDto validateTokenRequestDto) {
        return null;
    }
}
