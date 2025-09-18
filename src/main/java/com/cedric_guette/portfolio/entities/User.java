package com.cedric_guette.portfolio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;

    private String password;

    private String temporaryPassword;

    private String role;

    private LocalDateTime createdDate;

    private boolean firstConnexion;

    public enum Role {
        ROLE_ADMIN
    }
}
