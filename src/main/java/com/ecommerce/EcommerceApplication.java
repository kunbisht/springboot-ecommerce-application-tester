package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application class for the Spring Boot E-commerce Application.
 * 
 * This class serves as the entry point for the application and enables
 * various Spring Boot features including caching and JPA auditing.
 */
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class EcommerceApplication {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
}
