package com.tea.paradise.service.specification.impl;

import com.tea.paradise.dto.pagination.filters.ReviewFilter;
import com.tea.paradise.service.specification.Specification;
import com.tea.paradise.model.Review;
import com.tea.paradise.model.Users;
import com.tea.paradise.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewSpecification implements Specification<Review, ReviewFilter> {
    @Autowired
    private UserService userService;
    public static final String USER_PATH = "user";
    public static final String STARS_PATH = "stars";
    public static final String CREATED_DATE_PATH = "createdDate";

    @Override
    public List<Predicate> predicates(ReviewFilter filter, CriteriaBuilder criteriaBuilder, Root<Review> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.isByCurrentUser()) {
            Path<Users> userPath  = root.get(USER_PATH);
            predicates.add(criteriaBuilder.equal(userPath.get(ProductSpecification.ID_PATH), userService.getAuthInfo().getId()));
        }

        return predicates;
    }
}
