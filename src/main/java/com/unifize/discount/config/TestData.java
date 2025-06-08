package com.unifize.discount.config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.unifize.discount.model.*;

public class TestData {
    private final List<CartItem> cartItems;
    private final Customer customer;
    private final PaymentInfo payment;

    public TestData() {
        // Create test products
        Product pumaTshirt = Product.builder()
            .id(1)
            .brand("PUMA")
            .brandTier(BrandTier.PREMIUM)
            .category("T-shirt")
            .basePrice(new BigDecimal("2000"))
            .currentPrice(new BigDecimal("1200"))
            .build();

        // Create cart items
        this.cartItems = Arrays.asList(
            CartItem.builder()
                .product(pumaTshirt)
                .quantity(1)
                .size("M")
                .build()
        );

        // Create customer profile
        this.customer = Customer.builder()
            .id(1)
            .tier("REGULAR")
            .build();

        // Create payment info
        this.payment = PaymentInfo.builder()
            .method("CARD")
            .bankName("ICICI")
            .cardType("CREDIT")
            .build();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public PaymentInfo getPaymentInfo() {
        return payment;
    }
} 