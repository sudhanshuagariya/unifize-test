package com.example.unifize.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDataConfig {
    @Bean
    public TestData testData() {
        // Multiple Discount Scenario:
        // - PUMA T-shirt with "Min 40% off"
        // - Additional 10% off on T-shirts category
        // - ICICI bank offer of 10% instant discount
        return new TestData(); // Implement with appropriate test data
    }
} 