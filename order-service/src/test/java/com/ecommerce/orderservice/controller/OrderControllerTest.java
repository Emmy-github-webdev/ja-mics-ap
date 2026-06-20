package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequestDto;
import com.ecommerce.orderservice.dto.OrderResponseDto;
import com.ecommerce.orderservice.service.OrderService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

@Autowired
private MockMvc mockMvc;

@Autowired
private ObjectMapper objectMapper;

@MockBean
private OrderService orderService;

@Test
void shouldCreateOrder() throws Exception {

    OrderRequestDto request = new OrderRequestDto();
    request.setCustomerEmail("test@example.com");

    OrderResponseDto response = new OrderResponseDto();
    response.setId(1L);
    response.setCustomerEmail("test@example.com");
    response.setStatus("PENDING");
    response.setTotalAmount(100.0);

    when(orderService.createOrder(any(OrderRequestDto.class)))
            .thenReturn(response);

    mockMvc.perform(post("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.customerEmail").value("test@example.com"))
            .andExpect(jsonPath("$.status").value("PENDING"));
}

@Test
void shouldGetOrderById() throws Exception {

    OrderResponseDto response = new OrderResponseDto();
    response.setId(1L);
    response.setCustomerEmail("test@example.com");

    when(orderService.getOrder(1L))
            .thenReturn(Optional.of(response));

    mockMvc.perform(get("/orders/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.customerEmail").value("test@example.com"));
}

@Test
void shouldReturn404WhenOrderNotFound() throws Exception {

    when(orderService.getOrder(99L))
            .thenReturn(Optional.empty());

    mockMvc.perform(get("/orders/99"))
            .andExpect(status().isNotFound());
}

@Test
void shouldListOrders() throws Exception {

    OrderResponseDto order1 = new OrderResponseDto();
    order1.setId(1L);

    OrderResponseDto order2 = new OrderResponseDto();
    order2.setId(2L);

    when(orderService.listOrders())
            .thenReturn(List.of(order1, order2));

    mockMvc.perform(get("/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
}

}
