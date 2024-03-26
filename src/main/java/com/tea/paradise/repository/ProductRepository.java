package com.tea.paradise.repository;

import com.tea.paradise.dto.pagination.filters.ProductFilter;
import com.tea.paradise.enums.ProductSortType;
import com.tea.paradise.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        CriteriaRepository<Product, ProductFilter, ProductSortType> {
}
