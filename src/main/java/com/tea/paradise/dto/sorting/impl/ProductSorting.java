package com.tea.paradise.dto.sorting.impl;

import com.tea.paradise.dto.pagination.Sorter;
import com.tea.paradise.enums.ProductSortType;
import com.tea.paradise.model.Product;
import com.tea.paradise.dto.sorting.Sorting;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSorting implements Sorting<Product, ProductSortType> {

    @Override
    public List<Order> defSort(Sorter<ProductSortType> sorter, CriteriaBuilder criteriaBuilder, Root<Product> root) {
        List<Order> orders = new ArrayList<>();
        ProductSortType productSortType = sorter.getSortType();

        switch (productSortType) { // TODO implement IM DONE!
        }

        return orders;
    }
}
