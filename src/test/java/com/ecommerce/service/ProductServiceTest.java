package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
 * Tests business logic for product management operations.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setStockQuantity(10);
        testProduct.setCategory("Electronics");
        testProduct.setActive(true);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Another Product");
        product2.setDescription("Another Description");
        product2.setPrice(new BigDecimal("149.99"));
        product2.setStockQuantity(5);
        product2.setCategory("Books");
        product2.setActive(true);

        testProducts = Arrays.asList(testProduct, product2);
    }

    @Test
    void getAllProducts_ShouldReturnActiveProducts() {
        when(productRepository.findByActiveTrue()).thenReturn(testProducts);

        List<Product> result = productService.getAllProducts();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(testProducts);
        verify(productRepository).findByActiveTrue();
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        Optional<Product> result = productService.getProductById(1L);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testProduct);
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_WhenProductNotExists_ShouldReturnEmpty() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(999L);

        assertThat(result).isEmpty();
        verify(productRepository).findById(999L);
    }

    @Test
    void saveProduct_ShouldSaveAndReturnProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product result = productService.saveProduct(testProduct);

        assertThat(result).isEqualTo(testProduct);
        verify(productRepository).save(testProduct);
    }

    @Test
    void deleteProduct_WhenProductExists_ShouldSetActiveToFalse() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        productService.deleteProduct(1L);

        assertThat(testProduct.getActive()).isFalse();
        verify(productRepository).findById(1L);
        verify(productRepository).save(testProduct);
    }

    @Test
    void deleteProduct_WhenProductNotExists_ShouldDoNothing() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        productService.deleteProduct(999L);

        verify(productRepository).findById(999L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void searchProductsByName_ShouldReturnMatchingProducts() {
        when(productRepository.findByNameContainingIgnoreCaseAndActiveTrue("Test"))
                .thenReturn(Arrays.asList(testProduct));

        List<Product> result = productService.searchProductsByName("Test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(testProduct);
        verify(productRepository).findByNameContainingIgnoreCaseAndActiveTrue("Test");
    }

    @Test
    void getProductsByCategory_ShouldReturnProductsInCategory() {
        when(productRepository.findByCategoryAndActiveTrue("Electronics"))
                .thenReturn(Arrays.asList(testProduct));

        List<Product> result = productService.getProductsByCategory("Electronics");

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(testProduct);
        verify(productRepository).findByCategoryAndActiveTrue("Electronics");
    }

    @Test
    void isProductInStock_WhenSufficientStock_ShouldReturnTrue() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        boolean result = productService.isProductInStock(1L, 5);

        assertThat(result).isTrue();
        verify(productRepository).findById(1L);
    }

    @Test
    void isProductInStock_WhenInsufficientStock_ShouldReturnFalse() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        boolean result = productService.isProductInStock(1L, 15);

        assertThat(result).isFalse();
        verify(productRepository).findById(1L);
    }

    @Test
    void isProductInStock_WhenProductNotExists_ShouldReturnFalse() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        boolean result = productService.isProductInStock(999L, 5);

        assertThat(result).isFalse();
        verify(productRepository).findById(999L);
    }

    @Test
    void updateStock_WhenProductExists_ShouldUpdateStock() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product result = productService.updateStock(1L, 20);

        assertThat(result.getStockQuantity()).isEqualTo(20);
        verify(productRepository).findById(1L);
        verify(productRepository).save(testProduct);
    }

    @Test
    void updateStock_WhenProductNotExists_ShouldThrowException() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateStock(999L, 20))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Product not found with id: 999");

        verify(productRepository).findById(999L);
        verify(productRepository, never()).save(any(Product.class));
    }
}
