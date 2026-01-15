package com.terrasystem.user_shop_service.Auth;

import com.terrasystem.user_shop_service.Auth.DTO.AuthResponse;
import com.terrasystem.user_shop_service.Auth.DTO.AuthResult;
import com.terrasystem.user_shop_service.Auth.DTO.LoginRequest;
import com.terrasystem.user_shop_service.Auth.DTO.RegisterRequest;
import com.terrasystem.user_shop_service.Config.TooManyRequestsException;
import com.terrasystem.user_shop_service.Service.SecurityEventService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final SecurityEventService securityEventService;

    public AuthController(AuthService service, SecurityEventService securityEventService) {
        this.service = service;
        this.securityEventService = securityEventService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest req, HttpServletRequest httpReq) {
        try {
            AuthResult result = service.register(req);

            securityEventService.log(httpReq, "REGISTER_SUCCESS", "SUCCESS",
                    result.userId(), result.username(), null);

            return new AuthResponse(result.token());
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode().value() == 409) {
                securityEventService.log(httpReq, "REGISTER_CONFLICT", "FAIL",
                        null, req.username, "Username already exists");
            }
            throw ex;
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req, HttpServletRequest httpReq) {
        try {
            AuthResult result = service.login(req);

            securityEventService.log(httpReq, "LOGIN_SUCCESS", "SUCCESS",
                    result.userId(), result.username(), null);

            return new AuthResponse(result.token());
        } catch (TooManyRequestsException ex) {
            securityEventService.log(httpReq, "LOGIN_RATE_LIMITED", "BLOCKED",
                    null, req.username, "Too many login attempts");
            throw ex;
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                securityEventService.log(httpReq, "LOGIN_FAILURE", "FAIL",
                        null, req.username, "Invalid username or password");
            }
            throw ex;
        }
    }
}
