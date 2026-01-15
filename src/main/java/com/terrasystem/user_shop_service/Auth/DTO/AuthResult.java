package com.terrasystem.user_shop_service.Auth.DTO;

public record AuthResult(String token, Integer userId, String username, String role) {
}
