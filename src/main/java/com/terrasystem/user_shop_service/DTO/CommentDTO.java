package com.terrasystem.user_shop_service.DTO;

import java.time.Instant;

public class CommentDTO {
    public Integer id;
    public Integer itemId;
    public Integer userId;
    public String username;
    public String content;
    public Instant createdAt;
}
