package com.example.bankapi.Config.SecurityConfig;

<<<<<<< HEAD
import com.example.bankapi.Controller.Config.ExceptionHandle.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
=======
import com.example.bankapi.ExceptionHandle.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
<<<<<<< HEAD
import org.springframework.web.cors.CorsConfigurationSource;
=======
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
>>>>>>> 8bf2d517198aeb5b5c93bc71d0821bb6b2eddbb3

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AccessDeniedException accessDeniedException;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors->cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/actuator/**",
                                "/public/**",
                                "/permit-url",
                                "/v3/api-docs/**",
                                "/v3/api-docs/public/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                                )
                        .permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedException))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
