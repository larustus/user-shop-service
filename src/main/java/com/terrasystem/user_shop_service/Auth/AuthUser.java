package com.terrasystem.user_shop_service.Auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "auth_users",
    uniqueConstraints = @UniqueConstraint(name = "uk_auth_users_username", columnNames = "username")
)
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    // Na start prosto: "USER" / "ADMIN"
    @Column(nullable = false, length = 16)
    private String role = "USER";
}
