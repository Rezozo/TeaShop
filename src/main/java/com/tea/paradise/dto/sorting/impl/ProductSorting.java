package com.tea.paradise.dto.sorting.impl;

import com.tea.paradise.dto.pagination.Sorter;
import com.tea.paradise.enums.ProductSortType;
import com.tea.paradise.model.Orders;
import com.tea.paradise.model.Product;
import com.tea.paradise.dto.sorting.Sorting;
import com.tea.paradise.model.Review;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.tea.paradise.dto.specification.impl.ProductSpecification.*;

@Component
public class ProductSorting implements Sorting<Product, ProductSortType> {

    @Override
    public List<Order> defSort(Sorter<ProductSortType> sorter, CriteriaBuilder criteriaBuilder, Root<Product> root) {
        List<Order> orders = new ArrayList<>();
        ProductSortType productSortType = sorter.getSortType();

        switch (productSortType) {
            case CHEAP:
                Join<Product, Package> cheapPackageJoin = root.join(PACKAGE_PATH);
                Expression<Double> minPrice = criteriaBuilder.min(cheapPackageJoin.get(PRICE_PATH));
                orders.add(criteriaBuilder.asc(minPrice));
                break;
            case EXPENSIVE:
                Join<Product, Package> expensivePackageJoin = root.join(PACKAGE_PATH);
                Expression<Double> maxPrice = criteriaBuilder.max(expensivePackageJoin.get(PRICE_PATH));
                orders.add(criteriaBuilder.desc(maxPrice));
                break;
            case POPULAR:
                Join<Product, Package> popularPackageJoin = root.join(PACKAGE_PATH);
                Expression<Long> productCount = criteriaBuilder.count(popularPackageJoin.get(ID_PATH));
                orders.add(criteriaBuilder.desc(productCount));
                break;
            case MORE_RATE:
                Join<Product, Review> reviewJoin = root.join(REVIEW_PATH);
                Expression<Double> avgRate = criteriaBuilder.avg(reviewJoin.get(RATE_PATH));
                orders.add(criteriaBuilder.desc(avgRate));
                break;
        }

        return orders;
    }
}

