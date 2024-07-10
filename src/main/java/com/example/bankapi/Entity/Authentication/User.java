package com.example.bankapi.Entity.Authentication;

import com.example.bankapi.Entity.BankAccount.Account;
<<<<<<< HEAD
=======
import com.example.bankapi.Entity.BankAccount.CheckingAccount;
import com.example.bankapi.Entity.BankAccount.SavingAccount;
import com.fasterxml.jackson.annotation.JsonManagedReference;
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
<<<<<<< HEAD
import org.hibernate.annotations.ColumnDefault;
=======
import lombok.NoArgsConstructor;
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
<<<<<<< HEAD
=======
@NoArgsConstructor
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String phone;
    private String fullName;
<<<<<<< HEAD
    @ColumnDefault("false")
    private Boolean isVerified;
=======
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
    @Enumerated
    private ROLE role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();
<<<<<<< HEAD
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UserVerificationToken> verificationToken;
=======
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
<<<<<<< HEAD
    public User(){
        this.isVerified= false;
    }
=======
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
}
