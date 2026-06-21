package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.OrderItemDto;
import com.ecommerce.orderservice.dto.OrderResponseDto;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;

import java.util.List;

public final class OrderMapper {

    private OrderMapper() {}

    public static OrderResponseDto toDto(Order order) {

        OrderResponseDto dto = new OrderResponseDto();

        dto.setId(order.getId());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDto> items = order.getItems()
                .stream()
                .map(i -> {
                    OrderItemDto d = new OrderItemDto();
                    d.setProductId(i.getProductId());
                    d.setProductName(i.getProductName());
                    d.setQuantity(i.getQuantity());
                    d.setUnitPrice(i.getUnitPrice());
                    return d;
                })
                .toList();

        dto.setItems(items);

        return dto;
    }
}