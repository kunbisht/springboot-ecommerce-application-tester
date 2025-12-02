package com.ecommerce.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cache configuration for the application.
 * 
 * Configures caching strategy and cache managers.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configures the cache manager.
     * 
     * @return the cache manager
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("products", "product");
    }
}
