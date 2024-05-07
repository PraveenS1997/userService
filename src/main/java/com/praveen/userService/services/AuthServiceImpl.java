package com.praveen.userService.services;

import com.praveen.userService.configs.UserServiceConfiguration;
import com.praveen.userService.dtos.*;
import com.praveen.userService.exceptions.UserAlreadyExistException;
import com.praveen.userService.exceptions.UserNotFoundException;
import com.praveen.userService.models.Session;
import com.praveen.userService.models.SessionStatus;
import com.praveen.userService.models.User;
import com.praveen.userService.repositories.SessionRepository;
import com.praveen.userService.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Optional;

@Service
@Primary
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserServiceConfiguration userServiceConfiguration;

    public AuthServiceImpl(UserRepository userRepository,
                           SessionRepository sessionRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, UserServiceConfiguration userServiceConfiguration) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userServiceConfiguration = userServiceConfiguration;
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
        String jws = generateAuthToken(user);

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

        Optional<Session> optionalSession = sessionRepository.findByToken(logoutRequestDto.getToken());

        // Todo: token validation will be done in the other method
        if(optionalSession.isEmpty()){
            return;
        }

        Session session = optionalSession.get();
        session.setStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
    }

    @Override
    public ValidateTokenResponseDto validateToken(ValidateTokenRequestDto validateTokenRequestDto) {
        return null;
    }

    private String generateAuthToken(User user) {
        SecretKey secretKey = Keys
                .hmacShaKeyFor(userServiceConfiguration.getSecretKey().getBytes());

        HashMap<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId().toString());
        claims.put("expiryAt", String.valueOf(System.currentTimeMillis() + (long) userServiceConfiguration.getTokenExpirationInMinutes() * 60 * 1000));

        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }
}
