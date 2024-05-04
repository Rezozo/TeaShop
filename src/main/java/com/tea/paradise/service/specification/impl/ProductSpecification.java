package com.tea.paradise.service.specification.impl;

import com.tea.paradise.dto.pagination.filters.ProductFilter;
import com.tea.paradise.model.*;
import com.tea.paradise.model.Package;
import com.tea.paradise.service.UserService;
import com.tea.paradise.service.specification.Specification;
import com.tea.paradise.enums.VariantType;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProductSpecification implements Specification<Product, ProductFilter> {
    public static final String ID_PATH = "id";
    public static final String TITLE_PATH = "title";
    public static final String CATEGORY_PATH = "category";
    public static final String PACKAGE_PATH = "packages";
    public static final String VARIANT_PATH = "variant";
    public static final String PRICE_PATH = "price";
    public static final String REVIEW_PATH = "reviews";
    public static final String STARS_PATH = "stars";
    public static final String USER_PATH = "favoriteUsers";
    public static final String CREATED_DATE_PATH = "createdDate";
    public static final String PRODUCT_PATH = "product";
    public static final String ACTIVE_PATH = "active";
    public static final String QUANTITY_PATH = "quantity";
    public static final String PACKAGE_ORDERS_PATH = "packageOrders";

    @Autowired
    private UserService userService;

    @Override
    public List<Predicate> predicates(ProductFilter filter, CriteriaBuilder criteriaBuilder, Root<Product> root) {
        List<Predicate> predicates = new ArrayList<>();
        Path<Category> categoryPath  = root.get(CATEGORY_PATH);
        Path<Package> packagePath = root.get(PACKAGE_PATH);
        Path<Double> pricePath = packagePath.get(PRICE_PATH);
        Path<Variant> variantPath = packagePath.get(VARIANT_PATH);
        Path<Users> usersPath = root.get(USER_PATH);
        Path<ZonedDateTime> zonedDateTimePath = root.get(CREATED_DATE_PATH);
        Path<Boolean> active = root.get(ACTIVE_PATH);

        if (Objects.nonNull(filter.getIsActive())) {
            if (filter.getIsActive()) {
                predicates.add(criteriaBuilder.isTrue(active));
            } else {
                predicates.add(criteriaBuilder.isFalse(active));
            }
        } else {
            predicates.add(criteriaBuilder.isTrue(active));
        }

        if (filter.isOnlyFavorite()) {
            Long userId = userService.getAuthInfo().getId();
            predicates.add(criteriaBuilder.equal(usersPath.get(ID_PATH), userId));
        }

        if (filter.isOnlyNew()) {
            predicates.add(criteriaBuilder.between(zonedDateTimePath, ZonedDateTime.now().minusMonths(1L), ZonedDateTime.now()));
        }

        if (filter.isOnlyPopular()) {
            Subquery<Long> reviewCountSubquery  = criteriaBuilder.createQuery().subquery(Long.class);
            Root<Review> reviewRoot  = reviewCountSubquery.from(Review.class);
            reviewCountSubquery.select(criteriaBuilder.count(reviewRoot.get(ID_PATH)))
                    .where(criteriaBuilder.equal(reviewRoot.get(PRODUCT_PATH), root));

            Subquery<Long> favoriteCountSubquery = criteriaBuilder.createQuery().subquery(Long.class);
            Root<Product> productRoot = favoriteCountSubquery.from(Product.class);
            Join<Product, Users> favoriteJoin = productRoot.join(USER_PATH, JoinType.LEFT);
            favoriteCountSubquery.select(criteriaBuilder.count(favoriteJoin.get(ID_PATH)))
                    .where(criteriaBuilder.equal(productRoot, root));

            predicates.add(
                    criteriaBuilder.or(
                            criteriaBuilder.greaterThanOrEqualTo(reviewCountSubquery, 5L),
                            criteriaBuilder.greaterThanOrEqualTo(favoriteCountSubquery, 5L)
                    )
            );
        }

        String searchString = Optional.ofNullable(filter.getSearchString()).map(String::trim).orElse("");
        if (!StringUtils.isEmpty(searchString)) {
            String pattern = "%" + searchString + "%";
            Predicate title = criteriaBuilder.like(criteriaBuilder.upper(root.get(TITLE_PATH)), pattern.toUpperCase());
            predicates.add(criteriaBuilder.or(title));
        }

        if (Objects.nonNull(filter.getCategoryId())) {
            predicates.add(criteriaBuilder.equal(categoryPath.get(ID_PATH), filter.getCategoryId()));
        }

        if (Objects.nonNull(filter.getMinPrice())) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(pricePath, filter.getMinPrice()));
        }

        if (Objects.nonNull(filter.getMaxPrice())) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(pricePath, filter.getMaxPrice()));
        }

        if (Objects.nonNull(filter.getVariantTypes()) && !filter.getVariantTypes().isEmpty()) {
            CriteriaBuilder.In<VariantType> variantTitles = criteriaBuilder.in(variantPath.get(TITLE_PATH));
            for (VariantType name: filter.getVariantTypes()) {
                variantTitles = variantTitles.value(name);
            }
            predicates.add(variantTitles);
        }

        return predicates;
    }
}
