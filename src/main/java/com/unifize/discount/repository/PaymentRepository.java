package com.unifize.discount.repository;

import com.unifize.discount.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    // Additional query methods can be defined here if needed
}
