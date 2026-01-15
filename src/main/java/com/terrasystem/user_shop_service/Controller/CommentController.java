package com.terrasystem.user_shop_service.Controller;

import com.terrasystem.user_shop_service.DTO.CreateCommentRequest;
import com.terrasystem.user_shop_service.Entity.Comment;
import com.terrasystem.user_shop_service.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items/{itemId}/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Comment> list(@PathVariable Integer itemId) {
        return service.listForItem(itemId);
    }

    @PostMapping
    public Comment add(@PathVariable Integer itemId,
                       @Valid @RequestBody CreateCommentRequest req,
                       Authentication auth) {

        // Zakładam, że w JWTAuthFilter ustawiasz principal/Authentication z userId + role.
        // Przykładowo: auth.getName() może być userId (String).
        Integer userId = Integer.valueOf(auth.getName());

        // Jeśli w auth trzymasz też username jako claim/attribute, pobierz.
        // Minimalnie możesz tu wstawić stałe "user" albo później rozbudować.
        String username = "user-" + userId;

        return service.add(itemId, userId, username, req);
    }
}
