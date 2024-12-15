package com.lucky.art.repository;

import com.lucky.art.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryId(String categoryId);
}
