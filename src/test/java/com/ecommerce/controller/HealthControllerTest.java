package com.ecommerce.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureTestMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the HealthController.
 * Tests health check and ping endpoints.
 */
@SpringBootTest
@AutoConfigureTestMockMvc
@ActiveProfiles("test")
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test the health endpoint returns correct status.
     */
    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.application").value("Spring Boot E-commerce Application"))
                .andExpect(jsonPath("$.version").value("1.0.0"));
    }

    /**
     * Test the ping endpoint returns correct response.
     */
    @Test
    void testPingEndpoint() throws Exception {
        mockMvc.perform(get("/api/health/ping"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("pong"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}