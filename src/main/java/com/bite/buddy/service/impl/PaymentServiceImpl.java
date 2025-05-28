package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Order;
import com.bite.buddy.entity.Payment;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.PaymentDto;
import com.bite.buddy.repository.OrderRepo;
import com.bite.buddy.repository.PaymentRepo;
import com.bite.buddy.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final ModelMapper modelMapper;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private OrderRepo orderRepo;

    public PaymentServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentDto addPayment(Map<String, Object> requestMap) {
        PaymentDto dto = (PaymentDto) requestMap.get("payment");
        String orderId = requestMap.get("orderId").toString();
        Order order = orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setOrder(order);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        payment.setPaymentId("PA." + n);
        return modelMapper.map(paymentRepo.save(payment), PaymentDto.class);
    }

    @Override
    public PaymentDto updatePayment(Map<String, Object> requestMap) {
        String paymentId = requestMap.get("paymentId").toString();
        PaymentDto dto = (PaymentDto) requestMap.get("payment");
        Payment payment = paymentRepo.findByPaymentId(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));
        modelMapper.map(dto, payment);
        return modelMapper.map(paymentRepo.save(payment), PaymentDto.class);
    }

    @Override
    public PaymentDto getPaymentByOrder(Map<String, Object> requestMap) {
        String orderId = requestMap.get("orderId").toString();
        Payment payment = paymentRepo.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", orderId));
        return modelMapper.map(payment, PaymentDto.class);
    }

    @Override
    public void deletePayment(Map<String, Object> requestMap) {
        String paymentId = requestMap.get("paymentId").toString();
        Payment payment = paymentRepo.findByPaymentId(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", paymentId));
        paymentRepo.delete(payment);
    }
}

