package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.PaymentRequest;
import com.ecommerce.paymentservice.dto.PaymentResponseDto;
import com.ecommerce.paymentservice.mapper.PaymentMapper;
import com.ecommerce.paymentservice.model.PaymentTransaction;
import com.ecommerce.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(
            @RequestBody PaymentRequest request) {

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setOrderId(request.getOrderId());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());

        PaymentTransaction saved =
                paymentService.processPayment(transaction);

        return ResponseEntity.ok(PaymentMapper.toDto(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getPayment(
            @PathVariable Long id) {

        return paymentService.findById(id)
                .map(PaymentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> listPayments() {

        List<PaymentResponseDto> responses =
                paymentService.listPayments()
                        .stream()
                        .map(PaymentMapper::toDto)
                        .toList();

        return ResponseEntity.ok(responses);
    }
}