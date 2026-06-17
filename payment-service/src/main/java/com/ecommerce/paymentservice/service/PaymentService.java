package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.model.PaymentTransaction;
import com.ecommerce.paymentservice.repository.PaymentTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentTransactionRepository repository;

    public PaymentService(PaymentTransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PaymentTransaction processPayment(PaymentTransaction transaction) {
        transaction.setStatus("COMPLETED");
        return repository.save(transaction);
    }

    public Optional<PaymentTransaction> findById(Long id) {
        return repository.findById(id);
    }

    public List<PaymentTransaction> listPayments() {
        return repository.findAll();
    }
}
