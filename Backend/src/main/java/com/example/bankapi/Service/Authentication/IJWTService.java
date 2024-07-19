package com.example.bankapi.Service.Authentication;

import com.example.bankapi.Entity.Authentication.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJWTService {
    String extractUserName(String token);

    boolean isBlackListed(String token);

    Boolean isValidToken(String token,UserDetails userDetails);

    String generateToken(User user);

    void deleteToken(String token);
    String generateRefreshToken(User user);
    Boolean isExpiredToken(String token);

}
