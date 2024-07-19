package com.example.bankapi.Config.SecurityConfig;

import com.example.bankapi.Config.GlobalConfig.StaticVar;
import com.example.bankapi.Entity.Authentication.User;
import com.example.bankapi.Service.Authentication.IJWTService;
import com.example.bankapi.Service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.file.AccessDeniedException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final IJWTService jwtService;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver exceptionResolver;


    public JwtAuthenticationFilter(
            IJWTService jwtService,
            UserDetailsService userDetailsService,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver,
            UserService userService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.exceptionResolver = exceptionResolver;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken = request.getHeader("RefreshToken");
        try {
            logger.debug("Authorization header found: {}", authHeader); // Log header Authorization

            //xử lí request không có token
            if ((authHeader == null || !authHeader.startsWith("Bearer "))) {
                filterChain.doFilter(request, response);
                return;
            }
            if(refreshToken== null){
               throw new AccessDeniedException(StaticVar.MISSING_REFRESH_TOKEN_MESSAGE);
            }
            final String token = authHeader.substring(7);
            if (!(jwtService.isBlackListed(token) && jwtService.isBlackListed(refreshToken))) {
                try{
                    final String userEmail = jwtService.extractUserName(token);
                    logger.debug("Extracted token: {}", token);
                    logger.debug("Extracted userEmail: {}", userEmail);
                    handleNotExpiredAccessToken(userEmail,token);
                }catch (ExpiredJwtException e){
                    if(!jwtService.isExpiredToken(refreshToken)){
                        response=handleNotExpiredRefreshToken(request.getHeader("userEmail"),refreshToken,response);
                        jwtService.deleteToken(token);
                    }
                    else{
                        response = handleExpiredRefreshToken(request.getHeader("userEmail"), refreshToken, response);
                        handleExpiredAccessTokenAndRefreshToken(token, refreshToken);
                    }
                }
            } else {
                logger.warn("Token is blacklisted: {}", token);
                throw new AccessDeniedException(StaticVar.BLACKLISTED_TOKEN_MESSAGE);

            }

            logger.debug("Passing request to filter chain");
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private void handleExpiredAccessTokenAndRefreshToken(String token, String refreshToken) {
        jwtService.deleteToken(token);
        jwtService.deleteToken(refreshToken);
    }

    public void handleNotExpiredAccessToken(String userEmail,String token){
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            logger.debug("Token is not blacklisted"); // Log thông báo nếu token không trong blacklist

            // ... (Phần tải UserDetails) ...
            logger.debug("Loaded userDetails: {}", userDetails);
            if (jwtService.isValidToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    }
    public HttpServletResponse handleNotExpiredRefreshToken(String userEmail,String refreshToken,HttpServletResponse response) throws AccessDeniedException {
        try {
            // Xử lí để tái tạo AccessToken và RefreshToken mới từ RefreshToken
            User user = userService.findByEmail(userEmail);
            String newAccessToken = jwtService.generateToken(user);
            // Tạo đối tượng JSON để đại diện cho token mới
            JSONObject tokenResponse = new JSONObject();
            tokenResponse.put("newAccessToken", newAccessToken);

            // Thiết lập phản hồi
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.getWriter().write(tokenResponse.toString());
        } catch (Exception e) {
            logger.error("Error while refreshing tokens: " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            try {
                response.getWriter().write("{\"message\": \"Error while refreshing tokens\"}");
            } catch (IOException ioException) {
                logger.error("Error writing response: " + ioException.getMessage());
            }
        }
        return response;
    }
    public HttpServletResponse handleExpiredRefreshToken(String userEmail,String refreshToken,HttpServletResponse response) throws AccessDeniedException {
        try {
            // Xử lí để tái tạo AccessToken và RefreshToken mới từ RefreshToken
            User user = userService.findByEmail(userEmail);
            String newAccessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);
            // Tạo đối tượng JSON để đại diện cho token mới
            JSONObject tokenResponse = new JSONObject();
            tokenResponse.put("newAccessToken", newAccessToken);
            tokenResponse.put("newRefreshToken", newRefreshToken);
            // Thiết lập phản hồi
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.getWriter().write(tokenResponse.toString());
        } catch (Exception e) {
            logger.error("Error while refreshing tokens: " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            try {
                response.getWriter().write("{\"message\": \"Error while refreshing tokens\"}");
            } catch (IOException ioException) {
                logger.error("Error writing response: " + ioException.getMessage());
            }
        }
        return response;
    }


}


