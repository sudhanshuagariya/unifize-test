package com.unifize.discount.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "brand")
@Getter
@Setter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    public Integer brandId;
    @Column(name = "brand_name")
    public String brandName;
    @Column(name = "brand_discount")
    public BigDecimal brandDiscount;
    @Column(name = "brand_discount_name")
    public String brandDiscountName;
}
