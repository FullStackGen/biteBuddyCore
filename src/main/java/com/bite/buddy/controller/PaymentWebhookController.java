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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biteBuddy/customer/payment")
public class PaymentWebhookController {

    private final String endpointSecret = "whsec_..."; // Stripe webhook secret

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
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();
            String orderId = session.getMetadata().get("orderId"); // If you used metadata

            Order order = orderRepo.findByOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // Update payment status
            Payment payment = paymentRepo.findByOrder_OrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));
            payment.setStatus(ApplicationConstant.PaymentStatus.SUCCESS);
            paymentRepo.save(payment);

            // Update order status
            order.setStatus(ApplicationConstant.OrderStatus.CONFIRMED);
            orderRepo.save(order);
        }
        return "Success";
    }
}