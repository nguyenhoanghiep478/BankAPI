package com.example.bankapi.Repositories.Authentication;

import com.example.bankapi.Entity.Authentication.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerificationToken,Long> {
    Optional<UserVerificationToken> findByToken(String token);
}
