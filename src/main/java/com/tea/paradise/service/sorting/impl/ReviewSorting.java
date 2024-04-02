package com.tea.paradise.service.sorting.impl;

import com.tea.paradise.dto.pagination.Sorter;
import com.tea.paradise.service.sorting.Sorting;
import com.tea.paradise.service.specification.impl.ReviewSpecification;
import com.tea.paradise.enums.ReviewSortType;
import com.tea.paradise.model.Review;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewSorting implements Sorting<Review, ReviewSortType> {

    @Override
    public List<Order> defSort(Sorter<ReviewSortType> sorter, CriteriaBuilder criteriaBuilder, Root<Review> root) {
        List<Order> orders = new ArrayList<>();
        ReviewSortType sortType = sorter.getSortType();

        switch (sortType) {
            case NEW -> orders.add(criteriaBuilder.desc(root.get(ReviewSpecification.CREATED_DATE_PATH)));
            case OLD -> orders.add(criteriaBuilder.asc(root.get(ReviewSpecification.CREATED_DATE_PATH)));
            case POSITIVE -> orders.add(criteriaBuilder.desc(root.get(ReviewSpecification.STARS_PATH)));
            case NEGATIVE -> orders.add(criteriaBuilder.asc(root.get(ReviewSpecification.STARS_PATH)));
        }

        return orders;
    }
}
