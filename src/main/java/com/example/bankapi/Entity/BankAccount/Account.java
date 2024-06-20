package com.example.bankapi.Entity.BankAccount;

import com.example.bankapi.Entity.Authentication.User;
import com.example.bankapi.Entity.Receipt.Receipt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private double balance;
    private String accountType;
    private String accountNumber;
    private LocalDateTime createDate;
    @Column(length = 4,nullable = true)
    private String pin;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Receipt> receipts;

}
