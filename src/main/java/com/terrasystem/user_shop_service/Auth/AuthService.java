package com.terrasystem.user_shop_service.Auth;

import com.terrasystem.user_shop_service.Auth.DTO.AuthResult;
import com.terrasystem.user_shop_service.Auth.DTO.LoginRequest;
import com.terrasystem.user_shop_service.Auth.DTO.RegisterRequest;
import com.terrasystem.user_shop_service.Auth.JWT.JWTService;
import com.terrasystem.user_shop_service.Config.RateLimitService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final AuthUserRepository repo;
    private final PasswordEncoder encoder;
    private final JWTService jwtService;
    private final RateLimitService rateLimitService;

    public AuthService(AuthUserRepository repo,
                       PasswordEncoder encoder,
                       JWTService jwtService,
                       RateLimitService rateLimitService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.rateLimitService = rateLimitService;
    }

    public AuthResult register(RegisterRequest req) {
        if (repo.existsByUsername(req.username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        AuthUser u = new AuthUser();
        u.setUsername(req.username);
        u.setPasswordHash(encoder.encode(req.password));
        u.setRole("USER");

        AuthUser saved = repo.save(u);
        String token = jwtService.generateToken(saved.getId(), saved.getRole());

        return new AuthResult(token, saved.getId(), saved.getUsername(), saved.getRole());
    }

    public AuthResult login(LoginRequest req) {
        rateLimitService.consumeLoginAttemptForUser(req.username);

        AuthUser u = repo.findByUsername(req.username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid username or password"
                ));

        if (!encoder.matches(req.password, u.getPasswordHash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid username or password"
            );
        }

        String token = jwtService.generateToken(u.getId(), u.getRole());
        return new AuthResult(token, u.getId(), u.getUsername(), u.getRole());
    }
}
