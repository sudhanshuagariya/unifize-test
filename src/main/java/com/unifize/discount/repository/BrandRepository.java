package com.unifize.discount.repository;

import com.unifize.discount.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {
    /**
     * Method used to fine brand by brand name
     * @param name brand name
     * @return Branc
     */
    Optional<Brand> findByBrandName(String name);
}


