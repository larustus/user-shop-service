package com.terrasystem.user_shop_service.Auth;

import com.terrasystem.user_shop_service.Auth.DTO.AuthResponse;
import com.terrasystem.user_shop_service.Auth.DTO.LoginRequest;
import com.terrasystem.user_shop_service.Auth.DTO.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {
        return new AuthResponse(service.register(req));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return new AuthResponse(service.login(req));
    }
}
