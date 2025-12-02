package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing products in the e-commerce application.
 * 
 * Provides business logic for product operations including caching
 * and transaction management.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Retrieves all products with pagination.
     * 
     * @param pageable pagination parameters
     * @return paginated list of products
     */
    @Cacheable(value = "products", key = "#pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        log.info("Fetching products - page {} size {}", 
                 pageable.getPageNumber(), pageable.getPageSize());
        
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toDto);
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param id the product ID
     * @return the product
     * @throws ResourceNotFoundException if product not found
     */
    @Cacheable(value = "product", key = "#id")
    public ProductDto getProductById(Long id) {
        log.info("Fetching product with ID {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + id));
        
        return productMapper.toDto(product);
    }

    /**
     * Creates a new product.
     * 
     * @param productDto the product data
     * @return the created product
     */
    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating product with name {}", productDto.getName());
        
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        
        log.info("Product created with ID {}", savedProduct.getId());
        return productMapper.toDto(savedProduct);
    }

    /**
     * Updates an existing product.
     * 
     * @param id the product ID
     * @param productDto the updated product data
     * @return the updated product
     * @throws ResourceNotFoundException if product not found
     */
    @Transactional
    @CacheEvict(value = {"product", "products"}, allEntries = true)
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        log.info("Updating product with ID {}", id);
        
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + id));
        
        productMapper.updateEntityFromDto(productDto, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        
        log.info("Product updated with ID {}", updatedProduct.getId());
        return productMapper.toDto(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id the product ID
     * @throws ResourceNotFoundException if product not found
     */
    @Transactional
    @CacheEvict(value = {"product", "products"}, allEntries = true)
    public void deleteProduct(Long id) {
        log.info("Deleting product with ID {}", id);
        
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with ID " + id);
        }
        
        productRepository.deleteById(id);
        log.info("Product deleted with ID {}", id);
    }

    /**
     * Searches products by name.
     * 
     * @param name the product name to search for
     * @param pageable pagination parameters
     * @return paginated list of matching products
     */
    public Page<ProductDto> searchProductsByName(String name, Pageable pageable) {
        log.info("Searching products by name '{}'", name);
        
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(name, pageable);
        return products.map(productMapper::toDto);
    }
}
