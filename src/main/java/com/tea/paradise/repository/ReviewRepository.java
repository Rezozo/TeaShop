package com.tea.paradise.repository;

import com.tea.paradise.dto.pagination.filters.ReviewFilter;
import com.tea.paradise.enums.ReviewSortType;
import com.tea.paradise.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>,
        CriteriaRepository<Review, ReviewFilter, ReviewSortType>{
}
