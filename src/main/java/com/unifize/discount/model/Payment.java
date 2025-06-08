package com.unifize.discount.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "payment_info")
public class Payment {
    private String method;
    @Id
    @Column(name = "bank_name")
    private String bankName; // Optional
    @Column(name = "card_type")
    private String cardType; // Optional: CREDIT, DEBIT
    @Column(name = "discount_code")
    private BigDecimal discountCode;
    @Column(name = "discount_name")
    private String discountName;
} 