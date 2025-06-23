package com.example.crud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import com.example.crud.user.UserService;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private UserService userService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/public")
            .permitAll()
            .anyRequest().authenticated())
        .oauth2Login(oauth2 -> oauth2
            // .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService()))
            .successHandler((request, response, authentication) -> {
              OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
              userService.syncUser(oauth2User);
              response.sendRedirect("/"); // Redirect to home after login
            }))
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults())); // Enables JWT authentication

    return http.build();
  }

}
