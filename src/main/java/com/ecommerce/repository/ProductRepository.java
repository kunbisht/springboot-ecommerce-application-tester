package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Product entity.
 * 
 * Provides data access methods for product operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds products by name containing the specified string (case-insensitive).
     * 
     * @param name the name to search for
     * @param pageable pagination parameters
     * @return paginated list of matching products
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Finds products by category.
     * 
     * @param category the category to search for
     * @param pageable pagination parameters
     * @return paginated list of products in the category
     */
    Page<Product> findByCategory(String category, Pageable pageable);

    /**
     * Finds products by brand.
     * 
     * @param brand the brand to search for
     * @param pageable pagination parameters
     * @return paginated list of products from the brand
     */
    Page<Product> findByBrand(String brand, Pageable pageable);

    /**
     * Finds active products.
     * 
     * @param active the active status
     * @param pageable pagination parameters
     * @return paginated list of active products
     */
    Page<Product> findByActive(Boolean active, Pageable pageable);

    /**
     * Finds products within a price range.
     * 
     * @param minPrice minimum price
     * @param maxPrice maximum price
     * @param pageable pagination parameters
     * @return paginated list of products within price range
     */
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    /**
     * Finds products that are in stock (stock quantity > 0).
     * 
     * @param pageable pagination parameters
     * @return paginated list of products in stock
     */
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0")
    Page<Product> findProductsInStock(Pageable pageable);

    /**
     * Finds products by category and active status.
     * 
     * @param category the category
     * @param active the active status
     * @param pageable pagination parameters
     * @return paginated list of products
     */
    Page<Product> findByCategoryAndActive(String category, Boolean active, Pageable pageable);

    /**
     * Finds products with low stock (below threshold).
     * 
     * @param threshold the stock threshold
     * @return list of products with low stock
     */
    @Query("SELECT p FROM Product p WHERE p.stockQuantity < :threshold AND p.active = true")
    List<Product> findProductsWithLowStock(@Param("threshold") Integer threshold);

    /**
     * Counts products by category.
     * 
     * @param category the category
     * @return count of products in the category
     */
    long countByCategory(String category);

    /**
     * Finds a product by name (exact match, case-insensitive).
     * 
     * @param name the product name
     * @return optional product
     */
    Optional<Product> findByNameIgnoreCase(String name);
}
