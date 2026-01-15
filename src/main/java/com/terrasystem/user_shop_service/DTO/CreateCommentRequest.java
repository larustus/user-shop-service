package com.terrasystem.user_shop_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCommentRequest {
    @NotBlank(message = "Comment cannot be empty")
    @Size(min = 1, max = 1000, message = "Comment must be 1..1000 chars")
    public String content;
}
