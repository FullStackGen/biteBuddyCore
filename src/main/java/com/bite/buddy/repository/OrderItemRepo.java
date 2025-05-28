package com.bite.buddy.repository;

import com.bite.buddy.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepo extends JpaRepository<OrderItem, String> {
    Optional<List<OrderItem>> findByOrder_OrderId(String orderId);

    Optional<OrderItem> findByOrderItemId(String orderItemId);
}

