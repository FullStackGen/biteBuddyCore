package com.bite.buddy.service.impl;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService {

    @PostConstruct
    public void init() {
        Stripe.apiKey = "sk_test_51RfG4THCTBaV3QUluXuu6kptpbLt4VkQcmf6yhljbz4NWMNwl47qLfcnHdJZqfDEaPI9GfqSvAf3wYWZFbboGOHO00nwd7gdcu";
    }

    public String createCheckoutSession(String orderId, Double amount, String successUrl, String cancelUrl) throws Exception {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}&orderId=" + orderId)
                .setCancelUrl(cancelUrl)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("inr")
                                                .setUnitAmount((long) (amount * 100)) // Stripe expects paise
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Order " + orderId)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}