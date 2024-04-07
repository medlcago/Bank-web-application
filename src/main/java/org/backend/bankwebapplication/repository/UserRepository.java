package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username) OR LOWER(u.email) = LOWER(:email)")
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByResetPasswordToken(String token);
}