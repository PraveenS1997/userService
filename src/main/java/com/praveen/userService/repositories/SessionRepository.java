package com.praveen.userService.repositories;

import com.praveen.userService.models.Session;
import com.praveen.userService.models.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByToken(String token);

    Optional<Session> findByTokenAndUser_IdAndStatus(String token, Long userId, SessionStatus status);
}
