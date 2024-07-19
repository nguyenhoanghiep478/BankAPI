package com.example.bankapi.Config.GlobalConfig;

import com.example.bankapi.Repositories.Authentication.User;
import com.example.bankapi.Service.Authentication.impl.UserDetailsServiceImpl;
import com.example.bankapi.Service.Email.IEmailService;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppConfiguration {
    private final UserDetailsService userDetailsService;
    @Value("${FRONTEND_URL}")
    private String frontendURL;


    @Bean
    public Dotenv dotenv(){
        if(isRunningOnDocker()){
            return Dotenv.configure().directory("/app").load();
        }else{
            return Dotenv.configure().directory("/HiepRepositoryBank/BankAPI/Backend").load();
        }

    }
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
    private Boolean isRunningOnDocker() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("linux") ;
    }

    @PostConstruct
    public void init() {
        String currentDir = Paths.get("").toAbsolutePath().toString();
        System.out.println("Current working directory: " + currentDir);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }



    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Collections.singletonList(frontendURL));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONAL"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

}
