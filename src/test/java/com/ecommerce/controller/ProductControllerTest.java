package com.ecommerce.controller;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for ProductController.
 * 
 * Tests the REST endpoints for product management.
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto testProduct;
    private List<ProductDto> testProducts;

    @BeforeEach
    void setUp() {
        testProduct = ProductDto.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .stockQuantity(10)
                .category("Electronics")
                .brand("TestBrand")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testProducts = Arrays.asList(testProduct);
    }

    @Test
    void getAllProducts_ShouldReturnPagedProducts() throws Exception {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductDto> productPage = new PageImpl<>(testProducts, pageable, testProducts.size());
        when(productService.getAllProducts(any(Pageable.class))).thenReturn(productPage);

        // When & Then
        mockMvc.perform(get("/api/v1/products")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Test Product")))
                .andExpect(jsonPath("$.content[0].price", is(99.99)));

        verify(productService).getAllProducts(any(Pageable.class));
    }

    @Test
    void getProductById_ShouldReturnProduct() throws Exception {
        // Given
        when(productService.getProductById(1L)).thenReturn(testProduct);

        // When & Then
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Product")))
                .andExpect(jsonPath("$.price", is(99.99)));

        verify(productService).getProductById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        // Given
        ProductDto newProduct = ProductDto.builder()
                .name("New Product")
                .description("New Description")
                .price(new BigDecimal("149.99"))
                .stockQuantity(5)
                .category("Electronics")
                .brand("NewBrand")
                .build();

        ProductDto createdProduct = ProductDto.builder()
                .id(2L)
                .name("New Product")
                .description("New Description")
                .price(new BigDecimal("149.99"))
                .stockQuantity(5)
                .category("Electronics")
                .brand("NewBrand")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(productService.createProduct(any(ProductDto.class))).thenReturn(createdProduct);

        // When & Then
        mockMvc.perform(post("/api/v1/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("New Product")));

        verify(productService).createProduct(any(ProductDto.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        // Given
        ProductDto updatedProduct = ProductDto.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("199.99"))
                .stockQuantity(15)
                .category("Electronics")
                .brand("UpdatedBrand")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(productService.updateProduct(anyLong(), any(ProductDto.class))).thenReturn(updatedProduct);

        // When & Then
        mockMvc.perform(put("/api/v1/products/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpected(jsonPath("$.name", is("Updated Product")));

        verify(productService).updateProduct(eq(1L), any(ProductDto.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        // Given
        doNothing().when(productService).deleteProduct(1L);

        // When & Then
        mockMvc.perform(delete("/api/v1/products/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(1L);
    }

    @Test
    void searchProductsByName_ShouldReturnMatchingProducts() throws Exception {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductDto> productPage = new PageImpl<>(testProducts, pageable, testProducts.size());
        when(productService.searchProductsByName(eq("Test"), any(Pageable.class))).thenReturn(productPage);

        // When & Then
        mockMvc.perform(get("/api/v1/products/search")
                        .param("name", "Test")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", containsString("Test")));

        verify(productService).searchProductsByName(eq("Test"), any(Pageable.class));
    }
}
