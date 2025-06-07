package com.example.unifize.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.unifize.model.CartItem;
import com.example.unifize.model.CustomerProfile;
import com.example.unifize.model.DiscountedPrice;
import com.example.unifize.model.PaymentInfo;
import com.example.unifize.exception.DiscountCalculationException;
import com.example.unifize.exception.DiscountValidationException;

@Service
public interface DiscountService {
    /**
     * Calculate final price after applying discount logic:
     * - First apply brand/category discounts
     * - Then apply coupon codes
     * - Then apply bank offers
     *
     * @param cartItems List of items in the cart
     * @param customer Customer profile information
     * @param paymentInfo Optional payment information
     * @return Calculated discounted price details
     * @throws DiscountCalculationException if calculation fails
     */
    DiscountedPrice calculateCartDiscounts(
        List<CartItem> cartItems,
        CustomerProfile customer,
        Optional<PaymentInfo> paymentInfo
    ) throws DiscountCalculationException;

    /**
     * Validate if a discount code can be applied.
     * Handle specific cases like:
     * - Brand exclusions
     * - Category restrictions
     * - Customer tier requirements
     *
     * @param code Discount code to validate
     * @param cartItems Current cart items
     * @param customer Customer profile
     * @return true if code is valid, false otherwise
     * @throws DiscountValidationException if validation fails
     */
    boolean validateDiscountCode(
        String code,
        List<CartItem> cartItems,
        CustomerProfile customer
    ) throws DiscountValidationException;
} 