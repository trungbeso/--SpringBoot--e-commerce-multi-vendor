package com.benjamin.repository;

import com.benjamin.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryModel, Long> {
    CategoryModel findByCategoryId(String categoryId);
}
