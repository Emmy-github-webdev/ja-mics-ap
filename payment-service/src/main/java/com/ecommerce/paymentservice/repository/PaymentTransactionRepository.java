package com.ecommerce.paymentservice.repository;

import com.ecommerce.paymentservice.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}
