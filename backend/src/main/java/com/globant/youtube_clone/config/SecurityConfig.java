package com.globant.youtube_clone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration @EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    "/api/videos", "/api/videos/{videoId}",
                                    "/api/user/{userId}", "api/videos/{videoId}/comments")
                            .permitAll(); // Permitir acceso sin autenticación
                    requests.anyRequest().authenticated();
                }).oauth2ResourceServer(
                        oauth2 -> {
                            oauth2.jwt(Customizer.withDefaults());
                        }
                ).csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .cors(Customizer.withDefaults());
        return http.build();
    }

}