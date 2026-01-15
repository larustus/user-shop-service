package com.terrasystem.user_shop_service.Repository;

import com.terrasystem.user_shop_service.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByItemIdOrderByCreatedAtDesc(Integer itemId);
}
