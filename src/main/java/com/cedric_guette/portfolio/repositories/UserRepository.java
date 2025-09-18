package com.cedric_guette.portfolio.repositories;

import com.cedric_guette.portfolio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
