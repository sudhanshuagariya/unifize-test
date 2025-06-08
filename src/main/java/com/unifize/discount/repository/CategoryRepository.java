package com.unifize.discount.repository;

import com.unifize.discount.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    /**
     * Method used to find category by category name
     * @param name category name
     * @return Category
     */
    Optional<Category> findByCategoryName(String name);
}
