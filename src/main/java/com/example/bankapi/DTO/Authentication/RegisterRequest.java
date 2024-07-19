package com.example.bankapi.DTO.Authentication;

import com.example.bankapi.Entity.Authentication.ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not formated")
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8,message = "Password must have al least 8 character")
    private String password;
    @NotEmpty(message = "FullName is required")
    private String fullName;
    private String phone;
    private ROLE role;

}
