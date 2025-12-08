package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Spring Boot E-commerce Application.
 * This class serves as the entry point for the application.
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