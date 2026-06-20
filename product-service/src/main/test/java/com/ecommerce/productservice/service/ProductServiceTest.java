package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldSaveProduct() {

        ProductDto dto = new ProductDto();
        dto.setName("Laptop");
        dto.setDescription("Gaming laptop");
        dto.setPrice(BigDecimal.valueOf(1200));
        dto.setStock(5);

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Laptop");
        saved.setDescription("Gaming laptop");
        saved.setPrice(BigDecimal.valueOf(1200));
        saved.setStock(5);

        when(productRepository.save(any(Product.class)))
                .thenReturn(saved);

        ProductDto result = productService.save(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldFindById() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Phone");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Optional<ProductDto> result = productService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<ProductDto> result = productService.findById(99L);

        assertTrue(result.isEmpty());

        verify(productRepository).findById(99L);
    }

    @Test
    void shouldFindAllProducts() {

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("A");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("B");

        when(productRepository.findAll())
                .thenReturn(List.of(p1, p2));

        List<ProductDto> result = productService.findAll();

        assertEquals(2, result.size());

        verify(productRepository).findAll();
    }
}