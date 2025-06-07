package com.example.unifize.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Product {
    private String id;
    private String brand;
    private BrandTier brandTier;
    private String category;
    private BigDecimal basePrice;
    private BigDecimal currentPrice; // After brand/category discount
} 