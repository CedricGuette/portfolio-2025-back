package com.cedric_guette.portfolio.configuration;

import com.cedric_guette.portfolio.filter.JwtFilter;
import com.cedric_guette.portfolio.services.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;
    private final JwtUtils jwtUtils;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    // On crée un Bean pour gérer les autorisations lors des requêtes
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/presentation", "/api/weapon", "/api/devstat", "/api/project", "/api/auth/login","/api/contact/send", "/api/admin/password/initiate", "/api/admin/exists/*",  "/uploads/*").permitAll()
                                .requestMatchers("/api/contact/**", "/api/presentation/**", "/api/weapon/**", "/api/devstat/**", "/api/project/**", "/api/admin/password/change").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .cors(Customizer.withDefaults())
                .addFilterBefore(new JwtFilter(customUserDetailService, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}