package com.unifize.discount.model;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;
import lombok.Builder;

@Data
public class DiscountedPrice {
    private BigDecimal originalPrice;
    private BigDecimal finalPrice;
    private Map<String, BigDecimal> appliedDiscounts; // discount_name -> amount
    private String message;
} 