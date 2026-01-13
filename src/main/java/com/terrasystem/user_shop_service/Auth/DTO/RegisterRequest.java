package com.terrasystem.user_shop_service.Auth.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 64)
    public String username;

    @NotBlank
    @Size(min = 8, max = 128)
    public String password;
}