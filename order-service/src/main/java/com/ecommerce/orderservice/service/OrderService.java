package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderItemDto;
import com.ecommerce.orderservice.dto.OrderRequestDto;
import com.ecommerce.orderservice.dto.OrderResponseDto;
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
    public OrderResponseDto createOrder(OrderRequestDto request) {

        Order order = new Order();
        order.setCustomerEmail(request.getCustomerEmail());
        order.setStatus("PENDING");

        if (request.getItems() != null) {
            List<OrderItem> items = request.getItems().stream()
                    .map(itemDto -> {
                        OrderItem item = new OrderItem();
                        item.setProductId(itemDto.getProductId());
                        item.setProductName(itemDto.getProductName());
                        item.setQuantity(itemDto.getQuantity());
                        item.setUnitPrice(itemDto.getUnitPrice());
                        item.setOrder(order);
                        return item;
                    })
                    .toList();

            order.setItems(items);
        }

        double total = order.getItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();

        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        return toResponseDto(savedOrder);
    }

    @Cacheable(value = "orders", key = "#id")
    public Optional<OrderResponseDto> getOrder(Long id) {
        return orderRepository.findById(id)
                .map(this::toResponseDto);
    }

    public List<OrderResponseDto> listOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    private OrderResponseDto toResponseDto(Order order) {

        OrderResponseDto dto = new OrderResponseDto();

        dto.setId(order.getId());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDto> itemDtos = order.getItems()
                .stream()
                .map(item -> {
                    OrderItemDto itemDto = new OrderItemDto();
                    itemDto.setProductId(item.getProductId());
                    itemDto.setProductName(item.getProductName());
                    itemDto.setQuantity(item.getQuantity());
                    itemDto.setUnitPrice(item.getUnitPrice());
                    return itemDto;
                })
                .toList();

        dto.setItems(itemDtos);

        return dto;
    }
}