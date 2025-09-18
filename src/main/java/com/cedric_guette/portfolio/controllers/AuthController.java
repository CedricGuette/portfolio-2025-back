package com.cedric_guette.portfolio.controllers;

import com.cedric_guette.portfolio.configuration.HttpHeadersCORS;
import com.cedric_guette.portfolio.configuration.JwtUtils;
import com.cedric_guette.portfolio.entities.User;
import com.cedric_guette.portfolio.exceptions.UsernameAndPasswordDoNotMatchException;
import com.cedric_guette.portfolio.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "${app.url-front}")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final HttpHeadersCORS httpHeadersCORS = new HttpHeadersCORS();
    private final String headers = String.valueOf(httpHeadersCORS.headers());
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user){
        user.setUsername(user.getUsername().toLowerCase());

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if(authentication.isAuthenticated()){

                Map<String, Object> authData = new HashMap<>();
                authData.put("token", jwtUtils.generateToken(user.getUsername()));
                authData.put("type", "Bearer");

                logger.info("L'admin s'est connecté avec succès.");

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header(headers)
                        .body(authData);
            }

            throw new UsernameAndPasswordDoNotMatchException("L'identifiant et le mot de passe ne correspondent pas.");
        } catch (AuthenticationException e) {
            throw new UsernameAndPasswordDoNotMatchException("L'identifiant et le mot de passe ne correspondent pas.");
        }
    }
}
