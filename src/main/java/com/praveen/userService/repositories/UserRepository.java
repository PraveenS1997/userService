package com.praveen.userService.repositories;

import com.praveen.userService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);
}
