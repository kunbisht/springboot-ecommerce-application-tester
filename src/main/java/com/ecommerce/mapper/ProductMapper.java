package com.ecommerce.mapper;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Product entity and ProductDto.
 * 
 * Provides methods to map between entity and DTO objects.
 */
@Component
public class ProductMapper {

    /**
     * Converts Product entity to ProductDto.
     * 
     * @param product the product entity
     * @return the product DTO
     */
    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .brand(product.getBrand())
                .imageUrl(product.getImageUrl())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    /**
     * Converts ProductDto to Product entity.
     * 
     * @param productDto the product DTO
     * @return the product entity
     */
    public Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stockQuantity(productDto.getStockQuantity())
                .category(productDto.getCategory())
                .brand(productDto.getBrand())
                .imageUrl(productDto.getImageUrl())
                .active(productDto.getActive() != null ? productDto.getActive() : true)
                .build();
    }

    /**
     * Updates an existing Product entity with data from ProductDto.
     * 
     * @param productDto the product DTO with updated data
     * @param existingProduct the existing product entity to update
     */
    public void updateEntityFromDto(ProductDto productDto, Product existingProduct) {
        if (productDto == null || existingProduct == null) {
            return;
        }

        if (productDto.getName() != null) {
            existingProduct.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            existingProduct.setDescription(productDto.getDescription());
        }
        if (productDto.getPrice() != null) {
            existingProduct.setPrice(productDto.getPrice());
        }
        if (productDto.getStockQuantity() != null) {
            existingProduct.setStockQuantity(productDto.getStockQuantity());
        }
        if (productDto.getCategory() != null) {
            existingProduct.setCategory(productDto.getCategory());
        }
        if (productDto.getBrand() != null) {
            existingProduct.setBrand(productDto.getBrand());
        }
        if (productDto.getImageUrl() != null) {
            existingProduct.setImageUrl(productDto.getImageUrl());
        }
        if (productDto.getActive() != null) {
            existingProduct.setActive(productDto.getActive());
        }
    }
}
