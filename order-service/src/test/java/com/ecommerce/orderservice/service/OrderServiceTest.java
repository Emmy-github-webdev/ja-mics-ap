package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderItemDto;
import com.ecommerce.orderservice.dto.OrderRequestDto;
import com.ecommerce.orderservice.dto.OrderResponseDto;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.repository.OrderRepository;
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
class OrderServiceTest {

@Mock
private OrderRepository orderRepository;

@InjectMocks
private OrderService orderService;

@Test
void shouldCreateOrderSuccessfully() {

    OrderItemDto item = new OrderItemDto();
    item.setProductId(1L);
    item.setProductName("Laptop");
    item.setQuantity(2);
    item.setUnitPrice(1000.0);

    OrderRequestDto request = new OrderRequestDto();
    request.setCustomerEmail("test@example.com");
    request.setItems(List.of(item));

    Order savedOrder = new Order();
    savedOrder.setId(1L);
    savedOrder.setCustomerEmail("test@example.com");
    savedOrder.setStatus("PENDING");
    savedOrder.setTotalAmount(2000.0);

    OrderItem savedItem = new OrderItem();
    savedItem.setProductId(1L);
    savedItem.setProductName("Laptop");
    savedItem.setQuantity(2);
    savedItem.setUnitPrice(1000.0);

    savedOrder.setItems(List.of(savedItem));

    when(orderRepository.save(any(Order.class)))
            .thenReturn(savedOrder);

    OrderResponseDto response = orderService.createOrder(request);

    assertNotNull(response);
    assertEquals(1L, response.getId());
    assertEquals("test@example.com", response.getCustomerEmail());
    assertEquals("PENDING", response.getStatus());
    assertEquals(2000.0, response.getTotalAmount());

    verify(orderRepository).save(any(Order.class));
}

@Test
void shouldGetOrderById() {

    Order order = new Order();
    order.setId(1L);
    order.setCustomerEmail("test@example.com");
    order.setStatus("PENDING");
    order.setTotalAmount(500.0);
    order.setItems(List.of());

    when(orderRepository.findById(1L))
            .thenReturn(Optional.of(order));

    Optional<OrderResponseDto> result = orderService.getOrder(1L);

    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getId());

    verify(orderRepository).findById(1L);
}

@Test
void shouldReturnEmptyWhenOrderNotFound() {

    when(orderRepository.findById(99L))
            .thenReturn(Optional.empty());

    Optional<OrderResponseDto> result = orderService.getOrder(99L);

    assertTrue(result.isEmpty());

    verify(orderRepository).findById(99L);
}

@Test
void shouldListOrders() {

    Order order1 = new Order();
    order1.setId(1L);
    order1.setCustomerEmail("user1@test.com");
    order1.setStatus("PENDING");
    order1.setTotalAmount(100.0);
    order1.setItems(List.of());

    Order order2 = new Order();
    order2.setId(2L);
    order2.setCustomerEmail("user2@test.com");
    order2.setStatus("PENDING");
    order2.setTotalAmount(200.0);
    order2.setItems(List.of());

    when(orderRepository.findAll())
            .thenReturn(List.of(order1, order2));

    List<OrderResponseDto> result = orderService.listOrders();

    assertEquals(2, result.size());

    verify(orderRepository).findAll();
}

@Test
void shouldCreateOrderWithNoItems() {

    OrderRequestDto request = new OrderRequestDto();
    request.setCustomerEmail("empty@test.com");
    request.setItems(null);

    Order savedOrder = new Order();
    savedOrder.setId(10L);
    savedOrder.setCustomerEmail("empty@test.com");
    savedOrder.setStatus("PENDING");
    savedOrder.setTotalAmount(0.0);
    savedOrder.setItems(List.of());

    when(orderRepository.save(any(Order.class)))
            .thenReturn(savedOrder);

    OrderResponseDto response = orderService.createOrder(request);

    assertEquals(0.0, response.getTotalAmount());
    assertNotNull(response);
}
}