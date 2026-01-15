package com.terrasystem.user_shop_service.Service;

import com.terrasystem.user_shop_service.DTO.CommentDTO;
import com.terrasystem.user_shop_service.DTO.CreateCommentRequest;
import com.terrasystem.user_shop_service.Entity.Comment;
import com.terrasystem.user_shop_service.Repository.CommentRepository;
import com.terrasystem.user_shop_service.Repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final ItemRepository itemRepo;

    public CommentService(CommentRepository commentRepo, ItemRepository itemRepo) {
        this.commentRepo = commentRepo;
        this.itemRepo = itemRepo;
    }

    @Transactional(readOnly = true)
    public List<Comment> listForItem(Integer itemId) {
        return commentRepo.findByItemIdOrderByCreatedAtDesc(itemId);
    }

    @Transactional
    public Comment add(Integer itemId, Integer userId, String username, CreateCommentRequest req) {
        if (!itemRepo.existsById(itemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }

        Comment c = new Comment();
        c.setItemId(itemId);
        c.setUserId(userId);
        c.setUsername(username == null ? "unknown" : username);
        c.setContent(req.content.trim());
        c.setCreatedAt(Instant.now());

        return commentRepo.save(c);
    }
}
