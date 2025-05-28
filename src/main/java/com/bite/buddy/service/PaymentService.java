package com.bite.buddy.service;

import com.bite.buddy.model.PaymentDto;

import java.util.Map;

public interface PaymentService {

    PaymentDto addPayment(Map<String, Object> requestMap);

    PaymentDto updatePayment(Map<String, Object> requestMap);

    PaymentDto getPaymentByOrder(Map<String, Object> requestMap);

    void deletePayment(Map<String, Object> requestMap);
}

