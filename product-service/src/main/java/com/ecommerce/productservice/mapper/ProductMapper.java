package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.model.Product;

public final class ProductMapper {

    private ProductMapper() {}

    public static ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        return product;
    }
}