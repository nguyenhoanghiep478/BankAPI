package com.example.bankapi.DTO.Authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticateResponse {
    private String fullName;
    private String phone;
    private String token;
<<<<<<< HEAD
    private String refreshToken;
=======
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
    private String email;
}
