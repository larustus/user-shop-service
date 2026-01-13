package com.terrasystem.user_shop_service.Auth;

import com.terrasystem.user_shop_service.Auth.DTO.LoginRequest;
import com.terrasystem.user_shop_service.Auth.DTO.RegisterRequest;
import com.terrasystem.user_shop_service.Auth.JWT.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AuthService {

    private final AuthUserRepository repo;
    private final PasswordEncoder encoder;
    private final JWTService jwtService;

    public AuthService(AuthUserRepository repo,
                       PasswordEncoder encoder,
                       JWTService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest req) {
        if (repo.existsByUsername(req.username)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        AuthUser u = new AuthUser();
        u.setUsername(req.username);
        u.setPasswordHash(encoder.encode(req.password));
        u.setRole("USER");

        AuthUser saved = repo.save(u);
        return jwtService.generateToken(saved.getId(), saved.getRole());
    }

    public String login(LoginRequest req) {
        AuthUser u = repo.findByUsername(req.username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid credentials"
                ));

        if (!encoder.matches(req.password, u.getPasswordHash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid credentials"
            );
        }

        return jwtService.generateToken(u.getId(), u.getRole());
    }
}
