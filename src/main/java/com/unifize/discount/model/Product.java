package com.unifize.discount.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    @Column(name = "brand_tier")
    private BrandTier brandTier;
    private String category;
    @Column(name = "base_price")
    private BigDecimal basePrice;
    @Column(name = "current_price")
    private BigDecimal currentPrice; // After brand/category discount
} 