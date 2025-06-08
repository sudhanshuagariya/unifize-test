package com.unifize.discount.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unifize.discount.config.TestData;
import com.unifize.discount.exception.DiscountCalculationException;
import com.unifize.discount.exception.DiscountValidationException;
import com.unifize.discount.model.DiscountedPrice;
import com.unifize.discount.service.DiscountService;

@SpringBootTest
@ActiveProfiles("test")
class DiscountIntegrationTest {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private TestData testData;

    @Test
    @DisplayName("Should calculate discounts for complete cart flow")
    void calculateCartDiscounts_CompleteFlow() throws DiscountCalculationException {
        // Act
        DiscountedPrice result = discountService.calculateCartDiscounts(
            testData.getCartItems(),
            testData.getCustomer(),
            Optional.of(testData.getPaymentInfo())
        );

        // Assert
        assertNotNull(result);
        assertTrue(result.getOriginalPrice().compareTo(result.getFinalPrice()) > 0);
        assertNotNull(result.getAppliedDiscounts());
        assertFalse(result.getAppliedDiscounts().isEmpty());
    }

    @Test
    @DisplayName("Should validate discount code for complete flow")
    void validateDiscountCode_CompleteFlow() throws DiscountValidationException {
        // Act
        boolean result = discountService.validateDiscountCode(
            "SUMMER20",
            testData.getCartItems(),
            testData.getCustomer()
        );

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should handle empty cart scenario")
    void calculateCartDiscounts_EmptyCart() {
        // Act & Assert
        assertThrows(DiscountCalculationException.class, () ->
            discountService.calculateCartDiscounts(
                Arrays.asList(),
                testData.getCustomer(),
                Optional.of(testData.getPaymentInfo())
            )
        );
    }

    @Test
    @DisplayName("Should handle invalid discount code")
    void validateDiscountCode_InvalidCode() {
        // Act & Assert
        assertThrows(DiscountValidationException.class, () ->
            discountService.validateDiscountCode(
                "INVALID_CODE",
                testData.getCartItems(),
                testData.getCustomer()
            )
        );
    }

    @Test
    @DisplayName("Should handle null payment info")
    void calculateCartDiscounts_NullPaymentInfo() throws DiscountCalculationException {
        // Act
        DiscountedPrice result = discountService.calculateCartDiscounts(
            testData.getCartItems(),
            testData.getCustomer(),
            Optional.empty()
        );

        // Assert
        assertNotNull(result);
        assertTrue(result.getOriginalPrice().compareTo(result.getFinalPrice()) > 0);
    }
} 