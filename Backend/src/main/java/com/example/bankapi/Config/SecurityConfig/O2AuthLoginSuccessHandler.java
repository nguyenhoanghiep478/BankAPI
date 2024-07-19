package com.example.bankapi.Config.SecurityConfig;

import com.example.bankapi.Entity.Authentication.RegistationSource;
import com.example.bankapi.Entity.Authentication.User;
import com.example.bankapi.Service.Authentication.IAuthenticationService;
import com.example.bankapi.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.bankapi.Entity.Authentication.RegistationSource.GITHUB;

@Component
@RequiredArgsConstructor
public class O2AuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    private final UserService userService;

    @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            if("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
                DefaultOAuth2User principle = (DefaultOAuth2User) oAuth2AuthenticationToken.getPrincipal();
                Map<String, Object> attributes = principle.getAttributes();
                String email = (String) attributes.getOrDefault("email","");
                String name = (String) attributes.getOrDefault("name","");
                userService.findOptionalByEmail(email)
                        .ifPresentOrElse(user->{
                            DefaultOAuth2User oAuth2User = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),attributes,"name");
                            Authentication securityAuth = new OAuth2AuthenticationToken(oAuth2User,List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                    oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                            SecurityContextHolder.getContext().setAuthentication(securityAuth);
                        },()->{
                            User user = User.builder()
                                    .email(email)
                                    .fullName(name)
                                    .source(GITHUB)
                                    .isVerified(true)
                                    .build();
                           userService.save(user);
                           DefaultOAuth2User oAuth2User = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),attributes,"name");
                           Authentication securityAuth = new OAuth2AuthenticationToken(oAuth2User,List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                    oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                           SecurityContextHolder.getContext().setAuthentication(securityAuth);
                        });
            }
            this.setAlwaysUseDefaultTargetUrl(true);
           this.setDefaultTargetUrl("http://localhost:4200");
            super.onAuthenticationSuccess(request, response, authentication);

        }
}
