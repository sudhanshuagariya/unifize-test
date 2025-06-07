package com.example.unifize.config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import com.example.unifize.model.BrandTier;
import com.example.unifize.model.CartItem;
import com.example.unifize.model.CustomerProfile;
import com.example.unifize.model.PaymentInfo;
import com.example.unifize.model.Product;

public class TestData {
    private final List<CartItem> cartItems;
    private final CustomerProfile customer;
    private final PaymentInfo paymentInfo;

    public TestData() {
        // Create test products
        Product pumaTshirt = Product.builder()
            .id("P001")
            .brand("PUMA")
            .brandTier(BrandTier.PREMIUM)
            .category("T-shirt")
            .basePrice(new BigDecimal("2000"))
            .currentPrice(new BigDecimal("2000"))
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
        this.customer = CustomerProfile.builder()
            .id("C001")
            .tier("REGULAR")
            .build();

        // Create payment info
        this.paymentInfo = PaymentInfo.builder()
            .method("CARD")
            .bankName("ICICI")
            .cardType("CREDIT")
            .build();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public CustomerProfile getCustomer() {
        return customer;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }
} 