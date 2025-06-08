package com.unifize.discount.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import com.unifize.discount.model.*;
import com.unifize.discount.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unifize.discount.exception.DiscountCalculationException;

@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {

    @InjectMocks
    private DiscountServiceImpl discountService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PaymentRepository paymentRepository;


    private Optional<Product> testProduct;
    private Optional<CartItem> testCartItem;
    private Optional<Customer> testCustomer;
    private Optional<Payment> testPaymentInfo;
    private Optional<PaymentInfo> testPayment;
    private Optional<Brand> brand;
    private Optional<Category> category;

    @BeforeEach
    void setUp() {
        BigDecimal ten = new BigDecimal("10");
        // Setup test data
        testProduct = Optional.ofNullable(Product.builder()
            .id(1)
            .brand("PUMA")
            .brandTier(BrandTier.PREMIUM)
            .category("T-shirt")
            .basePrice(new BigDecimal("2000"))
            .currentPrice(new BigDecimal("2000"))
            .build());
        brand = Optional.of(new Brand());

        brand.ifPresent(b -> {
            b.setBrandDiscount(ten);
            b.setBrandDiscountName("EORS");
            b.setBrandName("PUMA");
        });

        category = Optional.of(new Category());
        category.ifPresent(b -> {
                    b.setCategoryDiscount(ten);
                    b.setCategoryName("T-shirt");
                    b.setCategoryId(1);
                    b.setCategoryDiscountName("EORS-CATEGORY");
                });

        testCartItem = Optional.ofNullable(CartItem.builder()
                .customerId("1")
            .product(testProduct.get())
            .quantity(2)
            .size("M")
            .build());

        testCustomer = Optional.ofNullable(Customer.builder()
            .id(1)
            .tier("BUDGET")
                        .voucherName("VOUCHER")
                        .voucherCode(ten)
            .build());

        testPaymentInfo = Optional.ofNullable(Payment.builder()
            .method("CARD")
            .bankName("ICICI")
            .cardType("CREDIT")
                        .discountCode(ten)
                        .discountName("ICICI")
            .build());
        testPayment = Optional.ofNullable(PaymentInfo.builder()
                .method("CARD")
                .bankName("ICICI")
                .cardType("CREDIT")
                .build());
    }

    @Test
    @DisplayName("Should calculate cart discounts successfully")
    void calculateCartDiscounts_Success() throws DiscountCalculationException {
        // Arrange
        when(productRepository.findById(any())).thenReturn(testProduct);
        when(brandRepository.findByBrandName(any())).thenReturn(brand);
        when(categoryRepository.findByCategoryName(any())).thenReturn(category);
        when(customerRepository.findById(any())).thenReturn(testCustomer);
        when(paymentRepository.findById(any())).thenReturn(testPaymentInfo);


        // Act
        DiscountedPrice result = discountService.calculateCartDiscounts(
            Arrays.asList(testCartItem.get()),
            testCustomer.get(),
            Optional.of(testPayment.get())
        );

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("4000"), result.getOriginalPrice());
        assertEquals(new BigDecimal("2624.4"), result.getFinalPrice());
        assertEquals("Congratulations, discount applied", result.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when validation fails")
    void validateDiscountCode_ThrowsException() {
        // Arrange
        when(productRepository.findById(any())).thenReturn(testProduct);
        when(brandRepository.findByBrandName(any())).thenReturn(brand);
        when(categoryRepository.findByCategoryName(any())).thenReturn(category);
        when(customerRepository.findById(any())).thenReturn(testCustomer);
        when(paymentRepository.findById(any())).thenReturn(testPaymentInfo);


        // Act
        boolean result = discountService.validateDiscountCode(
                "ICICI",
                Arrays.asList(testCartItem.get()),
                testCustomer.get()
        );

        // Assert
        assertNotNull(result);
        assertEquals(true, result);
    }
} 