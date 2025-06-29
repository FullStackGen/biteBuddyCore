package com.bite.buddy.entity;

import com.bite.buddy.configuration.ApplicationConstant.PaymentMethod;
import com.bite.buddy.configuration.ApplicationConstant.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    private String paymentId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    private String provider;            // STRIPE or RAZORPAY
    private String providerPaymentId;
}

