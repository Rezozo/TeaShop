package com.tea.paradise.repository.impl;

import com.tea.paradise.dto.pagination.filters.ProductFilter;
import com.tea.paradise.enums.ProductSortType;
import com.tea.paradise.model.Product;
import com.tea.paradise.repository.AbstractRepositoryImpl;

public class ProductRepositoryImpl extends AbstractRepositoryImpl<Product, ProductFilter, ProductSortType> {
    protected ProductRepositoryImpl() {
        super(Product.class);
    }
}
