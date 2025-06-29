package com.bite.buddy.configuration;

public class ApplicationConstant {

    public enum OrderStatus {
        PENDING,
        PENDING_PAYMENT,
        PAID,
        CANCELLED,
        CONFIRMED,
        DELIVERED
    }

    public enum PaymentStatus {PENDING, SUCCESS, FAILED}

    public enum PaymentMethod {CARD, UPI, WALLET}
}
