package com.praveen.userService.repositories;

import com.praveen.userService.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Modifying
    @Query("delete from Session s where s.id = ?1 and s.user.id = ?2")
    void deleteSessionByIdAndUserId(Long id, Long userId);
}
