package com.terrasystem.user_shop_service.Controller;

import com.terrasystem.user_shop_service.Entity.Order;
import com.terrasystem.user_shop_service.DTO.PlaceOrderRequest;
import com.terrasystem.user_shop_service.Service.OrderService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order placeOrder(Authentication authentication,
                            @Valid @RequestBody PlaceOrderRequest request) {
        Integer userId = Integer.valueOf(authentication.getName()); // sub
        return orderService.placeOrder(userId, request);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(
            @PathVariable Integer orderId,
            Authentication authentication
    ) {
        Integer userId = Integer.valueOf(authentication.getName());
        Order order = orderService.getOrderForUser(orderId, userId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public List<Order> getMyOrders(Authentication authentication) {
        Integer userId = Integer.valueOf(authentication.getName());
        return orderService.getOrdersForUser(userId);
    }

}
