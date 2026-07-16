package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.model.PaymentTransaction;
import com.ecommerce.paymentservice.repository.PaymentTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentTransactionRepository repository;

    @InjectMocks
    private PaymentService service;

    @Test
    void shouldProcessPayment() {

        PaymentTransaction tx = new PaymentTransaction();
        tx.setOrderId(1L);
        tx.setAmount(100.0);
        tx.setCurrency("USD");

        PaymentTransaction saved = new PaymentTransaction();
        saved.setId(1L);
        saved.setOrderId(1L);
        saved.setAmount(100.0);
        saved.setCurrency("USD");
        saved.setStatus("SUCCESS");

        when(repository.save(any(PaymentTransaction.class)))
                .thenReturn(saved);

        PaymentTransaction result = service.processPayment(tx);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("SUCCESS", result.getStatus());

        verify(repository).save(any(PaymentTransaction.class));
    }

    @Test
    void shouldFindById() {

        PaymentTransaction tx = new PaymentTransaction();
        tx.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(tx));

        Optional<PaymentTransaction> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());

        verify(repository).findById(1L);
    }

    @Test
    void shouldListPayments() {

        PaymentTransaction tx1 = new PaymentTransaction();
        tx1.setId(1L);

        PaymentTransaction tx2 = new PaymentTransaction();
        tx2.setId(2L);

        when(repository.findAll())
                .thenReturn(List.of(tx1, tx2));

        List<PaymentTransaction> result = service.listPayments();

        assertEquals(2, result.size());

        verify(repository).findAll();
    }
}