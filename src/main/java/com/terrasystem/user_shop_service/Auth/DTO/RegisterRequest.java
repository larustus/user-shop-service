package com.terrasystem.user_shop_service.Auth.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 64)
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,64}$", message = "Username has invalid format")
    public String username;

    @NotBlank
    @Size(min = 8, max = 128)
    public String password;
}