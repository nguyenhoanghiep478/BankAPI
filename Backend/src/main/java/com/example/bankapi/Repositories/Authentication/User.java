package com.example.bankapi.Repositories.Authentication;

import com.example.bankapi.Entity.Authentication.ROLE;
import com.example.bankapi.Entity.BankAccount.CheckingAccount;
import com.example.bankapi.Entity.BankAccount.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface User extends JpaRepository<com.example.bankapi.Entity.Authentication.User,Long> {
   Optional<com.example.bankapi.Entity.Authentication.User> findByEmail(String email);
    boolean existsByRole(ROLE role);
    boolean existsByEmail(String email);
    com.example.bankapi.Entity.Authentication.User save(User user);
}
