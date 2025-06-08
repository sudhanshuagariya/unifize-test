package com.unifize.discount.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CartItem {
    private String customerId;
    private Product product;
    private Integer quantity;
    private String size;
} 