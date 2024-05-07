package com.praveen.userService.services;

import com.praveen.userService.dtos.*;
import com.praveen.userService.exceptions.UserAlreadyExistException;
import com.praveen.userService.exceptions.UserNotFoundException;
import com.praveen.userService.models.Session;
import com.praveen.userService.models.SessionStatus;
import com.praveen.userService.models.User;
import com.praveen.userService.repositories.SessionRepository;
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
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthServiceImpl(UserRepository userRepository,
                           SessionRepository sessionRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        Assert.notNull(signUpRequestDto, "SignUpRequestDto is required");
        Assert.notNull(signUpRequestDto.getEmail(), "Email is required");
        Assert.notNull(signUpRequestDto.getName(), "Name is required");
        Assert.notNull(signUpRequestDto.getPassword(), "Password is required");

        Optional<User> user = userRepository.findUserByEmail(signUpRequestDto.getEmail());

        if(user.isEmpty()){
            throw new UserAlreadyExistException("Email " + signUpRequestDto.getEmail() + " already exists");
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
        Assert.notNull(email, "Email is required");
        Assert.notNull(password, "Password is required");

        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if(optionalUser.isEmpty() || !bCryptPasswordEncoder.matches(password, optionalUser.get().getPassword())){
            throw new UserNotFoundException("Invalid username and password");
        }

        User user = optionalUser.get();
        String jws = jwtTokenService.generateAuthToken(user);

        Session session = new Session();
        session.setToken(jws);
        session.setStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        sessionRepository.save(session);

        return LoginResponseDto.from(session);
    }

    @Override
    public void logout(LogoutRequestDto logoutRequestDto) {
        Assert.notNull(logoutRequestDto, "LoginRequestDto is required");
        Assert.notNull(logoutRequestDto.getToken(), "Token is required");
        Assert.notNull(logoutRequestDto.getUserId(), "User Id is required");

        Optional<Session> optionalSession = sessionRepository
                .findByTokenAndUser_IdAndStatus(logoutRequestDto.getToken(), logoutRequestDto.getUserId(), SessionStatus.ACTIVE);

        if(optionalSession.isEmpty()){
            return;
        }

        Session session = optionalSession.get();
        session.setStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
    }

    @Override
    public ValidateTokenResponseDto validateToken(ValidateTokenRequestDto validateTokenRequestDto) {
        Optional<Session> optionalSession = sessionRepository
                .findByTokenAndUser_IdAndStatus(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUserId(), SessionStatus.ACTIVE);

        if(optionalSession.isEmpty() || jwtTokenService.isTokenExpired(validateTokenRequestDto.getToken())){
            return new ValidateTokenResponseDto(SessionStatus.ENDED);
        }

        return new ValidateTokenResponseDto(SessionStatus.ACTIVE);
    }
}
