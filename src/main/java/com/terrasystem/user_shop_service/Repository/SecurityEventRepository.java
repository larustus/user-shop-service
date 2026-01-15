package com.terrasystem.user_shop_service.Repository;

import com.terrasystem.user_shop_service.Entity.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Integer> {
}
