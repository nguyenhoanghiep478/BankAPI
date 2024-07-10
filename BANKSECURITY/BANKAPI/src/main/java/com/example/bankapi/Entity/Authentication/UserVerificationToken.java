package com.example.bankapi.Entity.Authentication;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UserVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(length = StaticVar.VERIFICATION_TOKEN_LENGTH)
    private String token;
    private LocalDateTime expiryDate;
    public UserVerificationToken(){
        this.expiryDate= LocalDateTime.now().plusMinutes(1);
    }
}
