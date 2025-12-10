package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Product entity.
 * 
 * Provides data access methods for products in the e-commerce system.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find all active products.
     * 
     * @return List of active products
     */
    List<Product> findByActiveTrue();

    /**
     * Find products by name containing the given string (case-insensitive) and active status.
     * 
     * @param name Name to search for
     * @return List of matching active products
     */
    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    /**
     * Find products by category and active status.
     * 
     * @param category Product category
     * @return List of active products in the category
     */
    List<Product> findByCategoryAndActiveTrue(String category);

    /**
     * Find products with stock quantity greater than zero.
     * 
     * @return List of products in stock
     */
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0 AND p.active = true")
    List<Product> findProductsInStock();

    /**
     * Find products by price range.
     * 
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of products in the price range
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.active = true")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    /**
     * Count products by category.
     * 
     * @param category Product category
     * @return Number of products in the category
     */
    long countByCategoryAndActiveTrue(String category);
}
