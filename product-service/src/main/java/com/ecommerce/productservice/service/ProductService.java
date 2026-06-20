package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductDto save(ProductDto dto) {

        var saved = productRepository.save(
                ProductMapper.toEntity(dto)
        );

        return ProductMapper.toDto(saved);
    }

    @Cacheable(value = "products", key = "#id")
    public Optional<ProductDto> findById(Long id) {

        return productRepository.findById(id)
                .map(ProductMapper::toDto);
    }

    @Cacheable(value = "products")
    public List<ProductDto> findAll() {

        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }
}