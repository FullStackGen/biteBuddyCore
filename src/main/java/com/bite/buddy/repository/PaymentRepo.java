package com.bite.buddy.repository;

import com.bite.buddy.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment, String> {

    Optional<Payment> findByOrder_OrderId(String orderId);

    Optional<Payment> findByPaymentId(String paymentId);
}

