package com.praveen.userService.services;

import com.praveen.userService.dtos.LoginResponseDto;
import com.praveen.userService.dtos.LogoutRequestDto;
import com.praveen.userService.dtos.SignUpRequestDto;
import com.praveen.userService.dtos.UserDto;
import com.praveen.userService.exceptions.UserException;
import com.praveen.userService.models.Session;
import com.praveen.userService.models.SessionStatus;
import com.praveen.userService.models.User;
import com.praveen.userService.repositories.SessionRepository;
import com.praveen.userService.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Primary
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        User user = userRepository.findUserByEmail(signUpRequestDto.getEmail());

        if(user != null){
            throw new UserException("Email " + signUpRequestDto.getEmail() + " already exists");
        }

        User newUser = new User();
        newUser.setEmail(signUpRequestDto.getEmail());
        newUser.setName(signUpRequestDto.getName());
        newUser.setPassword(signUpRequestDto.getPassword());

        User savedUser = userRepository.save(newUser);
        return UserDto.from(savedUser);
    }

    @Override
    public LoginResponseDto login(String email, String password) {
        User user = userRepository.findUserByEmailAndPassword(email, password);

        if(user == null){
            throw new UserException("Invalid username and password");
        }

        Session session = new Session();
        session.setToken("someRandomToken");
        session.setStatus(SessionStatus.ACTIVE);
        session.setExpiringAt(new Date(System.currentTimeMillis() + 3600000));
        session.setUser(user);
        sessionRepository.save(session);

        return LoginResponseDto.from(session);
    }

    @Override
    @Transactional
    public void logout(LogoutRequestDto logoutRequestDto) {
        Optional<Session> optionalSession = sessionRepository.findById(logoutRequestDto.getSessionId());

        if(optionalSession.isEmpty()){
            throw new UserException("Invalid session id");
        }

        Session session = optionalSession.get();

        if(!session.getUser().getId().equals(logoutRequestDto.getUserId())){
            throw new UserException("Invalid user id");
        }

        if(!session.getToken().equals(logoutRequestDto.getToken())){
            throw new UserException("Invalid token");
        }

        sessionRepository.deleteSessionByIdAndUserId(logoutRequestDto.getSessionId(), logoutRequestDto.getUserId());
    }
}
