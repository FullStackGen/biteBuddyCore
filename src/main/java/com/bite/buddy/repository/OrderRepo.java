package com.bite.buddy.repository;

import com.bite.buddy.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, String> {

    Optional<List<Order>> findByUser_UserId(String userId);

    Optional<Order> findByOrderId(String orderId);
}