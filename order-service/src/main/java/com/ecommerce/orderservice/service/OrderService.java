package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(Order order) {
        double total = order.getItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(total);
        order.getItems().forEach(item -> item.setOrder(order));
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    @Cacheable(value = "orders", key = "#id")
    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}
