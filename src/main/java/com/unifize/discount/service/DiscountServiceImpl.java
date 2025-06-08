package com.unifize.discount.service;

import com.unifize.discount.exception.DiscountCalculationException;
import com.unifize.discount.exception.DiscountValidationException;
import com.unifize.discount.model.*;
import com.unifize.discount.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sudhanshu
 * @since 2025
 * @version 2.0
 */

import java.math.BigDecimal;
import java.util.*;

@Service
public class DiscountServiceImpl implements DiscountService{

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PaymentRepository paymentRepository;

    /**
     * Method used to calculate discounts and returns the discount price details
     * @param cartItems List of items in the cart
     * @param customer Customer profile information
     * @param paymentInfo Optional payment information
     * @return Calculated discount price details
     * @throws DiscountCalculationException Exception if product not found
     */
    @Override
    public DiscountedPrice calculateCartDiscounts(
            List<CartItem> cartItems,
            Customer customer,
            Optional<PaymentInfo> paymentInfo
    ) throws DiscountCalculationException {
        DiscountedPrice discountedPrice = new DiscountedPrice();
        BigDecimal finalPrice = BigDecimal.ZERO;
        BigDecimal basePrice = BigDecimal.ZERO;
        BigDecimal hundred = new BigDecimal("100");
        Map<String, BigDecimal> discounts = new HashMap<>();

            for(CartItem cartItem : cartItems) {

                // Checking whether product exists in DB or not
                Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow(() -> new DiscountCalculationException("Product not found"));
                BigDecimal currentBasePrice = product.getBasePrice().multiply( BigDecimal.valueOf(cartItem.getQuantity()));
                BigDecimal currentFinalPrice = currentBasePrice;


                // Brand and Category Discounts will be only applied for customers with tier = 'BUDGET'
                if(customer.getTier().equals(BrandTier.BUDGET.toString())){

                    // if product exist we find out the brand discount code and apply it
                    Optional<Brand> brand = brandRepository.findByBrandName(product.getBrand());
                    if(brand.isPresent()){
                        currentFinalPrice = currentFinalPrice.subtract (currentFinalPrice.multiply(brand.get().getBrandDiscount()).divide(hundred));
                        discounts.put(brand.get().getBrandDiscountName(),brand.get().getBrandDiscount());
                    }

                    // if product exist we find out the category discount code and apply it
                    Optional<Category> category = categoryRepository.findByCategoryName(product.getCategory());
                    if(category.isPresent()){
                        currentFinalPrice = currentFinalPrice.subtract (currentFinalPrice.multiply(category.get().getCategoryDiscount()).divide(hundred));
                        discounts.put(category.get().getCategoryDiscountName(),category.get().getCategoryDiscount());
                    }
                }
                basePrice=basePrice.add(currentBasePrice);
                finalPrice= finalPrice.add(currentFinalPrice);
            }


         // if customer has voucher codes
            Optional<Customer> customerProfile = customerRepository.findById(customer.getId());
            if(customerProfile.isPresent()) {
                BigDecimal voucherDiscount = customerProfile.get().getVoucherCode();
                finalPrice = finalPrice.subtract(finalPrice.multiply(voucherDiscount).divide(hundred));
                discounts.put(customerProfile.get().getVoucherName(),voucherDiscount);
            }

        // Applying discounts if payments method discounts present
        if(paymentInfo.isPresent()){
            Optional<Payment> payment = paymentRepository.findById(paymentInfo.get().getBankName());
            if(paymentInfo.isPresent()){
                finalPrice = finalPrice.subtract(finalPrice.multiply(payment.get().getDiscountCode()).divide(hundred));
                discounts.put(payment.get().getDiscountName(),payment.get().getDiscountCode());
            }
        }

        discountedPrice.setMessage(discounts.isEmpty() ? "No Discounts Applied" :"Congratulations, discount applied");
        discountedPrice.setAppliedDiscounts(discounts);
        discountedPrice.setFinalPrice(finalPrice);
        discountedPrice.setOriginalPrice(basePrice);
        return discountedPrice;
    }

    /**
     * Method used to validate that whether code is applicable or not for customer
     *
     * @param code Discount code to validate
     * @param cartItems Current cart items
     * @param customer Customer profile
     * @return true if code is applicable for the customer purchase
     * @throws DiscountValidationException Exception when code is not present or product not found
     */
    @Override
    public boolean validateDiscountCode(
            String code,
            List<CartItem> cartItems,
            Customer customer
    ) throws DiscountValidationException{
        if (code == null || code.isEmpty()) {
            throw new DiscountValidationException("Invalid discount code");
        }

        // for every item we will check that is there any discount for that category and brand
        for(CartItem cartItem: cartItems) {
            //will check that whether product exists
            Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow(() -> new DiscountValidationException("Product not found"));

            //checked whether  brand code exists in db for the product
            Optional<Brand> brand = brandRepository.findByBrandName(product.getBrand());
            if(brand.isPresent() && brand.get().getBrandName().equals(code)) return true;

            //checked whether  category code exists in db for the product
            Optional<Category> category = categoryRepository.findByCategoryName(product.getCategory());
            if(category.isPresent() && category.get().getCategoryName().equals(code)) return true;
        }

        //checked whether  voucher code exists in db for the customer
        Optional<Customer> customerProfile = customerRepository.findById(customer.getId());
        if(customerProfile.isPresent() && customerProfile.get().getVoucherName().equals(code)) return true;

        //checked whether bank offers exists in db for the customer
        Optional<Payment> paymentInfo = paymentRepository.findById(customer.getBank());
        if(paymentInfo.isPresent() && paymentInfo.get().getDiscountName().equals(code)) return true;

        //if code not found returns true
        return false;
    }
}
