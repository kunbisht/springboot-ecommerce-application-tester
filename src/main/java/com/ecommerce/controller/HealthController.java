package com.ecommerce.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Health check controller for monitoring application status.
 * 
 * Provides basic health endpoints for load balancers and monitoring systems.
 */
@RestController
@RequestMapping("/api/health")
@Slf4j
public class HealthController {

    /**
     * Basic health check endpoint.
     * 
     * @return health status response
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        log.debug("Health check requested");
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "ecommerce-application");
        response.put("version", "1.0.0");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Readiness probe endpoint.
     * 
     * @return readiness status
     */
    @GetMapping("/ready")
    public ResponseEntity<Map<String, String>> ready() {
        log.debug("Readiness check requested");
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "READY");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Liveness probe endpoint.
     * 
     * @return liveness status
     */
    @GetMapping("/live")
    public ResponseEntity<Map<String, String>> live() {
        log.debug("Liveness check requested");
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "ALIVE");
        
        return ResponseEntity.ok(response);
    }
}
