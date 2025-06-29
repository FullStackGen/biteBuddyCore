package com.bite.buddy.service;

import java.util.Map;

public interface PaymentService {

    String initiatePayment(Map<String, Object> requestMap);

    boolean verifyPayment(Map<String, Object> requestMap);

}