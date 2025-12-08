package com.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Health check controller for the e-commerce application.
 * Provides endpoints to check application health and status.
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    /**
     * Health check endpoint.
     *
     * @return ResponseEntity with health status
     */
    @GetMapping
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "Spring Boot E-commerce Application");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    /**
     * Simple ping endpoint.
     *
     * @return ResponseEntity with ping response
     */
    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "pong");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(response);
    }
}