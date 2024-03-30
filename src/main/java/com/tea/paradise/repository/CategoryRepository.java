package com.tea.paradise.repository;

import com.tea.paradise.enums.ParentCategory;
import com.tea.paradise.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByParentCategory(ParentCategory parentCategory);
}
