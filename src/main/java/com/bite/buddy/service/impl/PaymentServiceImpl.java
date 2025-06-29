package com.bite.buddy.service.impl;

import com.bite.buddy.configuration.ApplicationConstant;
import com.bite.buddy.entity.Order;
import com.bite.buddy.entity.Payment;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.repository.OrderRepo;
import com.bite.buddy.repository.PaymentRepo;
import com.bite.buddy.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private StripePaymentService stripePaymentService;

    @Value("${stripe.success.url}")
    private String stripeSuccessUrl;

    @Value("${stripe.failure.url}")
    private String stripeFailureUrl;

    @Override
    public String initiatePayment(Map<String, Object> requestMap) {
        String orderId = requestMap.get("orderId").toString();
        Order order = orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        if (!order.getStatus().equals(ApplicationConstant.OrderStatus.PENDING_PAYMENT)) {
            throw new IllegalStateException("Payment cannot be initiated for non-pending orders.");
        }

        try {
            String paymentUrl = stripePaymentService.createCheckoutSession(
                    orderId,
                    order.getTotalPrice(),
                    stripeSuccessUrl,
                    stripeFailureUrl
            );

            // Optionally update Payment entity
            Payment payment = new Payment();
            UUID id = UUID.randomUUID();
            long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
            payment.setPaymentId("PM." + n);
            payment.setOrder(order);
            payment.setAmount(order.getTotalPrice());
            payment.setStatus(ApplicationConstant.PaymentStatus.PENDING);
            payment.setMethod(ApplicationConstant.PaymentMethod.UPI);
            payment.setProvider("STRIPE");
            paymentRepo.save(payment);

            return paymentUrl;
        } catch (Exception e) {
            throw new RuntimeException("Stripe payment initiation failed: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyPayment(Map<String, Object> requestMap) {
        return false;
    }
}