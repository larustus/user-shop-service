package com.terrasystem.user_shop_service.Repository;

import com.terrasystem.user_shop_service.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
 }
