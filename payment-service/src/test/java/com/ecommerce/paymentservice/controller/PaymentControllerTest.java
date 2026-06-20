package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.PaymentRequest;
import com.ecommerce.paymentservice.model.PaymentTransaction;
import com.ecommerce.paymentservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @Test
    void shouldCreatePayment() throws Exception {

        PaymentRequest request = new PaymentRequest();
        request.setOrderId(1L);
        request.setAmount(100.0);
        request.setCurrency("USD");

        PaymentTransaction tx = new PaymentTransaction();
        tx.setId(1L);
        tx.setOrderId(1L);
        tx.setAmount(100.0);
        tx.setCurrency("USD");
        tx.setStatus("SUCCESS");

        when(paymentService.processPayment(any()))
                .thenReturn(tx);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void shouldGetPayment() throws Exception {

        PaymentTransaction tx = new PaymentTransaction();
        tx.setId(1L);
        tx.setOrderId(1L);
        tx.setAmount(100.0);
        tx.setCurrency("USD");
        tx.setStatus("SUCCESS");

        when(paymentService.findById(1L))
                .thenReturn(Optional.of(tx));

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenPaymentNotFound() throws Exception {

        when(paymentService.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/payments/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldListPayments() throws Exception {

        PaymentTransaction tx1 = new PaymentTransaction();
        tx1.setId(1L);

        PaymentTransaction tx2 = new PaymentTransaction();
        tx2.setId(2L);

        when(paymentService.listPayments())
                .thenReturn(List.of(tx1, tx2));

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}