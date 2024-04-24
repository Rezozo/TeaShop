package com.tea.paradise.service.sorting.impl;

import com.tea.paradise.dto.pagination.Sorter;
import com.tea.paradise.service.specification.impl.ProductSpecification;
import com.tea.paradise.enums.ProductSortType;
import com.tea.paradise.model.Product;
import com.tea.paradise.service.sorting.Sorting;
import com.tea.paradise.model.Review;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSorting implements Sorting<Product, ProductSortType> {

    @Override
    public List<Order> defSort(Sorter<ProductSortType> sorter, CriteriaBuilder criteriaBuilder, Root<Product> root) {
        List<Order> orders = new ArrayList<>();
        ProductSortType productSortType = sorter.getSortType();

        switch (productSortType) {
            case CHEAP:
                Join<Product, Package> cheapPackageJoin = root.join(ProductSpecification.PACKAGE_PATH);
                Expression<Double> minPrice = criteriaBuilder.min(cheapPackageJoin.get(ProductSpecification.PRICE_PATH));
                orders.add(criteriaBuilder.asc(minPrice));
                break;
            case EXPENSIVE:
                Join<Product, Package> expensivePackageJoin = root.join(ProductSpecification.PACKAGE_PATH);
                Expression<Double> maxPrice = criteriaBuilder.max(expensivePackageJoin.get(ProductSpecification.PRICE_PATH));
                orders.add(criteriaBuilder.desc(maxPrice));
                break;
            case POPULAR:
                Join<Product, Package> popularPackageJoin = root.join(ProductSpecification.PACKAGE_PATH);
                Expression<Long> productCount = criteriaBuilder.count(popularPackageJoin.get(ProductSpecification.ID_PATH));
                orders.add(criteriaBuilder.desc(productCount));
                break;
            case MORE_RATE:
                Join<Product, Review> reviewJoin = root.join(ProductSpecification.REVIEW_PATH, JoinType.LEFT);
                Expression<Double> avgRate = criteriaBuilder.coalesce(criteriaBuilder.avg(reviewJoin.get(ProductSpecification.STARS_PATH)), 0.0);
                orders.add(criteriaBuilder.desc(avgRate));
                break;
        }

        return orders;
    }
}

