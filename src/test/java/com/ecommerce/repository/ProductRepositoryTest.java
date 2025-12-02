package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for ProductRepository.
 * 
 * Tests the data access layer for product operations.
 */
@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Product testProduct1;
    private Product testProduct2;
    private Product testProduct3;

    @BeforeEach
    void setUp() {
        testProduct1 = Product.builder()
                .name("iPhone 15")
                .description("Latest Apple smartphone")
                .price(new BigDecimal("999.99"))
                .stockQuantity(10)
                .category("Electronics")
                .brand("Apple")
                .active(true)
                .build();

        testProduct2 = Product.builder()
                .name("Samsung Galaxy S24")
                .description("Android smartphone")
                .price(new BigDecimal("899.99"))
                .stockQuantity(0)
                .category("Electronics")
                .brand("Samsung")
                .active(true)
                .build();

        testProduct3 = Product.builder()
                .name("MacBook Pro")
                .description("Professional laptop")
                .price(new BigDecimal("1999.99"))
                .stockQuantity(5)
                .category("Computers")
                .brand("Apple")
                .active(false)
                .build();

        entityManager.persistAndFlush(testProduct1);
        entityManager.persistAndFlush(testProduct2);
        entityManager.persistAndFlush(testProduct3);
    }

    @Test
    void findByNameContainingIgnoreCase_ShouldReturnMatchingProducts() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> result = productRepository.findByNameContainingIgnoreCase("iphone", pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("iPhone 15");
    }

    @Test
    void findByCategory_ShouldReturnProductsInCategory() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> result = productRepository.findByCategory("Electronics", pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(Product::getCategory)
                .containsOnly("Electronics");
    }

    @Test
    void findByBrand_ShouldReturnProductsFromBrand() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> result = productRepository.findByBrand("Apple", pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(Product::getBrand)
                .containsOnly("Apple");
    }

    @Test
    void findByActive_ShouldReturnActiveProducts() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> result = productRepository.findByActive(true, pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(Product::getActive)
                .containsOnly(true);
    }

    @Test
    void findByPriceBetween_ShouldReturnProductsInPriceRange() {
        // Given
        BigDecimal minPrice = new BigDecimal("800.00");
        BigDecimal maxPrice = new BigDecimal("1000.00");
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> result = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(Product::getPrice)
                .allMatch(price -> price.compareTo(minPrice) >= 0 && price.compareTo(maxPrice) <= 0);
    }

    @Test
    void findProductsInStock_ShouldReturnProductsWithStock() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> result = productRepository.findProductsInStock(pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(Product::getStockQuantity)
                .allMatch(stock -> stock > 0);
    }

    @Test
    void findByCategoryAndActive_ShouldReturnActiveProductsInCategory() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> result = productRepository.findByCategoryAndActive("Electronics", true, pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .allMatch(product -> product.getCategory().equals("Electronics") && product.getActive());
    }

    @Test
    void findProductsWithLowStock_ShouldReturnProductsBelowThreshold() {
        // Given
        Integer threshold = 8;

        // When
        List<Product> result = productRepository.findProductsWithLowStock(threshold);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStockQuantity()).isLessThan(threshold);
        assertThat(result.get(0).getActive()).isTrue();
    }

    @Test
    void countByCategory_ShouldReturnCorrectCount() {
        // When
        long count = productRepository.countByCategory("Electronics");

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    void findByNameIgnoreCase_ShouldReturnExactMatch() {
        // When
        Optional<Product> result = productRepository.findByNameIgnoreCase("iphone 15");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("iPhone 15");
    }

    @Test
    void findByNameIgnoreCase_WhenNotFound_ShouldReturnEmpty() {
        // When
        Optional<Product> result = productRepository.findByNameIgnoreCase("Nonexistent Product");

        // Then
        assertThat(result).isEmpty();
    }
}
