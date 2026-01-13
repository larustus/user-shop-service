package com.terrasystem.user_shop_service.Auth.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    public String username;

    @NotBlank
    public String password;
}