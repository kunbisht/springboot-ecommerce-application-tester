package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ProductService.
 * 
 * Tests the business logic for product operations.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private ProductDto testProductDto;
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
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

        testProductDto = ProductDto.builder()
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
    void getAllProducts_ShouldReturnPagedProducts() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(testProducts, pageable, testProducts.size());
        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(productMapper.toDto(testProduct)).thenReturn(testProductDto);

        // When
        Page<ProductDto> result = productService.getAllProducts(pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Test Product");
        verify(productRepository).findAll(pageable);
        verify(productMapper).toDto(testProduct);
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productMapper.toDto(testProduct)).thenReturn(testProductDto);

        // When
        ProductDto result = productService.getProductById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Product");
        verify(productRepository).findById(1L);
        verify(productMapper).toDto(testProduct);
    }

    @Test
    void getProductById_WhenProductNotExists_ShouldThrowException() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.getProductById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with ID 1");
        
        verify(productRepository).findById(1L);
        verify(productMapper, never()).toDto(any());
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        // Given
        ProductDto newProductDto = ProductDto.builder()
                .name("New Product")
                .description("New Description")
                .price(new BigDecimal("149.99"))
                .stockQuantity(5)
                .category("Electronics")
                .brand("NewBrand")
                .build();

        Product newProduct = Product.builder()
                .name("New Product")
                .description("New Description")
                .price(new BigDecimal("149.99"))
                .stockQuantity(5)
                .category("Electronics")
                .brand("NewBrand")
                .active(true)
                .build();

        Product savedProduct = Product.builder()
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

        ProductDto savedProductDto = ProductDto.builder()
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

        when(productMapper.toEntity(newProductDto)).thenReturn(newProduct);
        when(productRepository.save(newProduct)).thenReturn(savedProduct);
        when(productMapper.toDto(savedProduct)).thenReturn(savedProductDto);

        // When
        ProductDto result = productService.createProduct(newProductDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("New Product");
        verify(productMapper).toEntity(newProductDto);
        verify(productRepository).save(newProduct);
        verify(productMapper).toDto(savedProduct);
    }

    @Test
    void updateProduct_WhenProductExists_ShouldReturnUpdatedProduct() {
        // Given
        ProductDto updateDto = ProductDto.builder()
                .name("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("199.99"))
                .stockQuantity(15)
                .category("Electronics")
                .brand("UpdatedBrand")
                .build();

        Product updatedProduct = Product.builder()
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

        ProductDto updatedProductDto = ProductDto.builder()
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

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        doNothing().when(productMapper).updateEntityFromDto(updateDto, testProduct);
        when(productRepository.save(testProduct)).thenReturn(updatedProduct);
        when(productMapper.toDto(updatedProduct)).thenReturn(updatedProductDto);

        // When
        ProductDto result = productService.updateProduct(1L, updateDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Updated Product");
        verify(productRepository).findById(1L);
        verify(productMapper).updateEntityFromDto(updateDto, testProduct);
        verify(productRepository).save(testProduct);
        verify(productMapper).toDto(updatedProduct);
    }

    @Test
    void updateProduct_WhenProductNotExists_ShouldThrowException() {
        // Given
        ProductDto updateDto = ProductDto.builder().name("Updated Product").build();
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> productService.updateProduct(1L, updateDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with ID 1");
        
        verify(productRepository).findById(1L);
        verify(productMapper, never()).updateEntityFromDto(any(), any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void deleteProduct_WhenProductExists_ShouldDeleteProduct() {
        // Given
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        // When
        productService.deleteProduct(1L);

        // Then
        verify(productRepository).existsById(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_WhenProductNotExists_ShouldThrowException() {
        // Given
        when(productRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> productService.deleteProduct(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with ID 1");
        
        verify(productRepository).existsById(1L);
        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    void searchProductsByName_ShouldReturnMatchingProducts() {
        // Given
        String searchName = "Test";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(testProducts, pageable, testProducts.size());
        when(productRepository.findByNameContainingIgnoreCase(searchName, pageable)).thenReturn(productPage);
        when(productMapper.toDto(testProduct)).thenReturn(testProductDto);

        // When
        Page<ProductDto> result = productService.searchProductsByName(searchName, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).contains("Test");
        verify(productRepository).findByNameContainingIgnoreCase(searchName, pageable);
        verify(productMapper).toDto(testProduct);
    }
}
