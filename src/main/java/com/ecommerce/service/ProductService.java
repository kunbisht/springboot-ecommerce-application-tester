package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Product business logic.
 * 
 * Handles all business operations related to products in the e-commerce system.
 */
@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Get all active products.
     * 
     * @return List of all active products
     */
    public List<Product> getAllProducts() {
        return productRepository.findByActiveTrue();
    }

    /**
     * Get product by ID.
     * 
     * @param id Product ID
     * @return Optional containing the product if found
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Save a product (create or update).
     * 
     * @param product Product to save
     * @return Saved product
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Delete a product by ID (soft delete).
     * 
     * @param id Product ID
     */
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product prod = product.get();
            prod.setActive(false);
            productRepository.save(prod);
        }
    }

    /**
     * Search products by name (case-insensitive).
     * 
     * @param name Product name to search
     * @return List of matching products
     */
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
    }

    /**
     * Get products by category.
     * 
     * @param category Product category
     * @return List of products in the category
     */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryAndActiveTrue(category);
    }

    /**
     * Check if product is in stock.
     * 
     * @param id Product ID
     * @param quantity Quantity to check
     * @return true if product has sufficient stock
     */
    public boolean isProductInStock(Long id, Integer quantity) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(p -> p.getStockQuantity() >= quantity).orElse(false);
    }

    /**
     * Update product stock quantity.
     * 
     * @param id Product ID
     * @param quantity New stock quantity
     * @return Updated product
     */
    public Product updateStock(Long id, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setStockQuantity(quantity);
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found with id: " + id);
    }
}
