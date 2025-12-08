package com.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Integration tests for the main application class.
 * Tests the basic application context loading.
 */
@SpringBootTest
@ActiveProfiles("test")
class EcommerceApplicationTests {

    /**
     * Test that the application context loads successfully.
     */
    @Test
    void contextLoads() {
        // This test will pass if the application context loads without errors
    }
}