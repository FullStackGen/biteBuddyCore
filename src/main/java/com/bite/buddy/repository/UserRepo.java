package com.bite.buddy.repository;

import com.bite.buddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);
}
