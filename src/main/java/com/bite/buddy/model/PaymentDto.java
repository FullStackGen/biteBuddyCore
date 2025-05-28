package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
    private String paymentId;
    private String orderId;
    private Double amount;
    private String status;
    private String method;
}

