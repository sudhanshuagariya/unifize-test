package com.example.unifize.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CartItem {
    private Product product;
    private int quantity;
    private String size;
} 