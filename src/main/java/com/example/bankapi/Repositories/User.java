package com.example.bankapi.Repositories;

import com.example.bankapi.Entity.Authentication.ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface User extends JpaRepository<com.example.bankapi.Entity.Authentication.User,Long> {
    Optional<com.example.bankapi.Entity.Authentication.User> findByEmail(String email);
    boolean existsByRole(ROLE role);
    boolean existsByEmail(String email);
}
