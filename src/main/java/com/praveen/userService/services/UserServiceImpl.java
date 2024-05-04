package com.praveen.userService.services;

import com.praveen.userService.dtos.LogoutDto;
import com.praveen.userService.dtos.UserDto;
import com.praveen.userService.exceptions.UserException;
import com.praveen.userService.models.Session;
import com.praveen.userService.models.User;
import com.praveen.userService.repositories.SessionRepository;
import com.praveen.userService.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public UserServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
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
    public Session loginUser(UserDto userDto) {
        User user = userRepository.findUserByEmailAndPassword(userDto.getEmail(), userDto.getPassword());

        if(user == null){
            throw new UserException("Invalid username and password");
        }

        Session session = new Session();
        session.setToken("someRandomToken");
        session.setValid(true);
        session.setUser(user);
        sessionRepository.save(session);

        return session;
    }

    @Override
    @Transactional
    public void logoutUser(LogoutDto logoutDto) {
        sessionRepository.deleteSessionByIdAndUserId(logoutDto.getSessionId(), logoutDto.getUserId());
    }
}
