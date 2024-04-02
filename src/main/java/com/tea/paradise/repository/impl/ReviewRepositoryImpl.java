package com.tea.paradise.repository.impl;

import com.tea.paradise.dto.pagination.filters.ReviewFilter;
import com.tea.paradise.enums.ReviewSortType;
import com.tea.paradise.model.Review;
import com.tea.paradise.repository.AbstractRepositoryImpl;

public class ReviewRepositoryImpl extends AbstractRepositoryImpl<Review, ReviewFilter, ReviewSortType> {
    protected ReviewRepositoryImpl() {
        super(Review.class);
    }
}
