package com.terrasystem.user_shop_service.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "comments", indexes = {
        @Index(name = "idx_comments_item", columnList = "item_id"),
        @Index(name = "idx_comments_user", columnList = "user_id")
})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "item_id", nullable = false)
    private Integer itemId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
