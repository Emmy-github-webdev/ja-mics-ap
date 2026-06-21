package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    void shouldCreateProduct() throws Exception {

        ProductDto request = new ProductDto();
        request.setName("Laptop");
        request.setPrice(BigDecimal.valueOf(1200));
        request.setStock(5);

        ProductDto response = new ProductDto();
        response.setId(1L);
        response.setName("Laptop");

        when(productService.save(any(ProductDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void shouldGetProductById() throws Exception {

        ProductDto dto = new ProductDto();
        dto.setId(1L);
        dto.setName("Phone");

        when(productService.findById(1L))
                .thenReturn(Optional.of(dto));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {

        when(productService.findById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldListProducts() throws Exception {

        ProductDto p1 = new ProductDto();
        p1.setId(1L);

        ProductDto p2 = new ProductDto();
        p2.setId(2L);

        when(productService.findAll())
                .thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}