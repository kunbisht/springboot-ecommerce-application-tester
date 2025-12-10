package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Spring Boot E-commerce Application.
 * 
 * This class serves as the entry point for the Spring Boot application.
 * It includes auto-configuration, component scanning, and configuration properties.
 */
@SpringBootApplication
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
