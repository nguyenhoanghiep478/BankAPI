package com.example.bankapi.DTO.Email;

import lombok.Data;

@Data
public class VerificationEmailRequest {
    String token;
    String email;
}
