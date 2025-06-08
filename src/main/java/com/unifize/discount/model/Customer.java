package com.unifize.discount.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@Entity
@Table(name =  "customer_profile")
@Builder
public class Customer {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tier;
    @Column(name = "voucher_code")
    private BigDecimal voucherCode;
    @Column(name = "voucher_name")
    private String voucherName;
    private String bank;
    // Add more fields as needed
} 