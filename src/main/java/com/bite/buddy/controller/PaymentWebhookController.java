package com.bite.buddy.controller;

import com.bite.buddy.configuration.ApplicationConstant;
import com.bite.buddy.entity.Order;
import com.bite.buddy.entity.Payment;
import com.bite.buddy.repository.OrderRepo;
import com.bite.buddy.repository.PaymentRepo;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret; // Stripe webhook secret

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @PostMapping("/webhook")
    public String handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (Exception e) {
            return "Invalid signature";
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElseThrow(() -> new RuntimeException("Session object not deserialized"));

            Map<String, String> metadata = session.getMetadata();
            if (metadata == null || !metadata.containsKey("orderId")) {
                throw new RuntimeException("Order ID metadata missing in session");
            }

            String orderId = metadata.get("orderId");

            Order order = orderRepo.findByOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

            Payment payment = paymentRepo.findByOrder_OrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Payment not found for order ID: " + orderId));

            payment.setStatus(ApplicationConstant.PaymentStatus.SUCCESS);
            paymentRepo.save(payment);

            order.setStatus(ApplicationConstant.OrderStatus.CONFIRMED);
            orderRepo.save(order);
        }
        return "Success";
    }
}