package com.unifize.discount.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {
    @Column(name = "category_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "category_discount")
    private BigDecimal categoryDiscount;
    @Column(name = "category_discount_name")
    private String categoryDiscountName;
}
