package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.model.PaymentTransaction;
import com.ecommerce.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("java:S4684")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentTransaction> createPayment(@RequestBody PaymentTransaction transaction) {
        return ResponseEntity.ok(paymentService.processPayment(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTransaction> getPayment(@PathVariable Long id) {
        return paymentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PaymentTransaction>> listPayments() {
        return ResponseEntity.ok(paymentService.listPayments());
    }
}
