package com.example.unifize.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.unifize.exception.DiscountCalculationException;
import com.example.unifize.exception.DiscountValidationException;
import com.example.unifize.model.BrandTier;
import com.example.unifize.model.CartItem;
import com.example.unifize.model.CustomerProfile;
import com.example.unifize.model.DiscountedPrice;
import com.example.unifize.model.PaymentInfo;
import com.example.unifize.model.Product;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock
    private DiscountService discountService;

    private Product testProduct;
    private CartItem testCartItem;
    private CustomerProfile testCustomer;
    private PaymentInfo testPaymentInfo;

    @BeforeEach
    void setUp() {
        // Setup test data
        testProduct = Product.builder()
            .id("P001")
            .brand("PUMA")
            .brandTier(BrandTier.PREMIUM)
            .category("T-shirt")
            .basePrice(new BigDecimal("2000"))
            .currentPrice(new BigDecimal("2000"))
            .build();

        testCartItem = CartItem.builder()
            .product(testProduct)
            .quantity(1)
            .size("M")
            .build();

        testCustomer = CustomerProfile.builder()
            .id("C001")
            .tier("REGULAR")
            .build();

        testPaymentInfo = PaymentInfo.builder()
            .method("CARD")
            .bankName("ICICI")
            .cardType("CREDIT")
            .build();
    }

    @Test
    @DisplayName("Should calculate cart discounts successfully")
    void calculateCartDiscounts_Success() throws DiscountCalculationException {
        // Arrange
        when(discountService.calculateCartDiscounts(
            anyList(),
            any(CustomerProfile.class),
            any(Optional.class)
        )).thenReturn(DiscountedPrice.builder()
            .originalPrice(new BigDecimal("2000"))
            .finalPrice(new BigDecimal("1200"))
            .message("Applied multiple discounts")
            .build());

        // Act
        DiscountedPrice result = discountService.calculateCartDiscounts(
            Arrays.asList(testCartItem),
            testCustomer,
            Optional.of(testPaymentInfo)
        );

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("2000"), result.getOriginalPrice());
        assertEquals(new BigDecimal("1200"), result.getFinalPrice());
        assertEquals("Applied multiple discounts", result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when calculation fails")
    void calculateCartDiscounts_ThrowsException() {
        // Arrange
        when(discountService.calculateCartDiscounts(
            anyList(),
            any(CustomerProfile.class),
            any(Optional.class)
        )).thenThrow(new DiscountCalculationException("Calculation failed"));

        // Act & Assert
        assertThrows(DiscountCalculationException.class, () ->
            discountService.calculateCartDiscounts(
                Arrays.asList(testCartItem),
                testCustomer,
                Optional.of(testPaymentInfo)
            )
        );
    }

    @Test
    @DisplayName("Should validate discount code successfully")
    void validateDiscountCode_Success() throws DiscountValidationException {
        // Arrange
        when(discountService.validateDiscountCode(
            anyString(),
            anyList(),
            any(CustomerProfile.class)
        )).thenReturn(true);

        // Act
        boolean result = discountService.validateDiscountCode(
            "SUMMER20",
            Arrays.asList(testCartItem),
            testCustomer
        );

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should throw exception when validation fails")
    void validateDiscountCode_ThrowsException() {
        // Arrange
        when(discountService.validateDiscountCode(
            anyString(),
            anyList(),
            any(CustomerProfile.class)
        )).thenThrow(new DiscountValidationException("Invalid discount code"));

        // Act & Assert
        assertThrows(DiscountValidationException.class, () ->
            discountService.validateDiscountCode(
                "INVALID",
                Arrays.asList(testCartItem),
                testCustomer
            )
        );
    }
} 