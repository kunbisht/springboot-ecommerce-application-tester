package com.ecommerce.controller;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * REST controller for managing products in the e-commerce application.
 * 
 * Provides endpoints for CRUD operations on products with proper
 * security controls and validation.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieves all products with pagination support.
     * 
     * @param pageable pagination parameters
     * @return paginated list of products
     */
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
        log.info("Fetching all products with pagination - page {} size {}", 
                 pageable.getPageNumber(), pageable.getPageSize());
        Page<ProductDto> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a specific product by its ID.
     * 
     * @param id the product ID
     * @return the product details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(
            @PathVariable @Min(value = 1, message = "Product ID must be positive") Long id) {
        log.info("Fetching product with ID {}", id);
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Creates a new product.
     * 
     * @param productDto the product data
     * @return the created product
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Creating new product with name {}", productDto.getName());
        ProductDto createdProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Updates an existing product.
     * 
     * @param id the product ID
     * @param productDto the updated product data
     * @return the updated product
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable @Min(value = 1, message = "Product ID must be positive") Long id,
            @Valid @RequestBody ProductDto productDto) {
        log.info("Updating product with ID {}", id);
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id the product ID
     * @return no content response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable @Min(value = 1, message = "Product ID must be positive") Long id) {
        log.info("Deleting product with ID {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches products by name.
     * 
     * @param name the product name to search for
     * @param pageable pagination parameters
     * @return paginated list of matching products
     */
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProductsByName(
            @RequestParam String name,
            Pageable pageable) {
        log.info("Searching products by name '{}'", name);
        Page<ProductDto> products = productService.searchProductsByName(name, pageable);
        return ResponseEntity.ok(products);
    }
}
