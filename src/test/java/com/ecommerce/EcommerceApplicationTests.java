package com.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Integration tests for the main application class.
 * 
 * Verifies that the Spring context loads successfully.
 */
@SpringBootTest
@ActiveProfiles("test")
class EcommerceApplicationTests {

    /**
     * Test that the application context loads successfully.
     */
    @Test
    void contextLoads() {
        // This test will fail if the application context cannot be loaded
    }
}
