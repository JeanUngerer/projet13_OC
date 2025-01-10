package com.yourcaryourway.chatappback.poc_back.repositories;

import com.yourcaryourway.chatappback.poc_back.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
