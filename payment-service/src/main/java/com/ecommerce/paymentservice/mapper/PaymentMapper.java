package com.ecommerce.paymentservice.mapper;

import com.ecommerce.paymentservice.dto.PaymentResponseDto;
import com.ecommerce.paymentservice.model.PaymentTransaction;

public final class PaymentMapper {

    private PaymentMapper() {}

    public static PaymentResponseDto toDto(PaymentTransaction t) {

        PaymentResponseDto r = new PaymentResponseDto();

        r.setId(t.getId());
        r.setOrderId(t.getOrderId());
        r.setAmount(t.getAmount());
        r.setCurrency(t.getCurrency());
        r.setStatus(t.getStatus());
        r.setProcessedAt(t.getProcessedAt());

        return r;
    }
}