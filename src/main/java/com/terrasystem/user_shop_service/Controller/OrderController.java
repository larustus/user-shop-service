package com.terrasystem.user_shop_service.Controller;

import com.terrasystem.user_shop_service.Entity.Order;
import com.terrasystem.user_shop_service.DTO.PlaceOrderRequest;
import com.terrasystem.user_shop_service.Service.OrderService;


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
    public Order placeOrder(
            @RequestBody PlaceOrderRequest request,
            Authentication auth
    ) {
        Integer userId = Integer.valueOf(auth.getName());
        return orderService.placeOrder(userId, request);
    }

    
    @GetMapping
    public List<Order> myOrders(Authentication auth) {
        Integer userId = Integer.valueOf(auth.getName());
        return orderService.getOrdersForUser(userId);
    }

    
    @GetMapping("/{orderId}")
    public Order getOrder(
            @PathVariable Integer orderId,
            Authentication auth
    ) {
        Integer userId = Integer.valueOf(auth.getName());
        return orderService.getOrderForUser(orderId, userId);
    }
}
