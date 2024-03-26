package com.tea.paradise.dto.specification.impl;

import com.tea.paradise.dto.pagination.filters.ProductFilter;
import com.tea.paradise.dto.specification.Specification;
import com.tea.paradise.enums.VariantType;
import com.tea.paradise.model.Category;
import com.tea.paradise.model.Package;
import com.tea.paradise.model.Product;
import com.tea.paradise.model.Variant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProductSpecification implements Specification<Product, ProductFilter> {
    private static final String ID_PATH = "id";
    private static final String TITLE_PATH = "title";
    private static final String CATEGORY_PATH = "category";
    private static final String PACKAGE_PATH = "packages";
    private static final String VARIANT_PATH = "variant";
    private static final String PRICE_PATH = "price";

    @Override
    public List<Predicate> predicates(ProductFilter filter, CriteriaBuilder criteriaBuilder, Root<Product> root) {
        List<Predicate> predicates = new ArrayList<>();
        Path<Category> categoryPath  = root.get(CATEGORY_PATH);
        Path<Package> packagePath = root.get(PACKAGE_PATH);
        Path<Double> pricePath = packagePath.get(PRICE_PATH);
        Path<Variant> variantPath = packagePath.get(VARIANT_PATH);

        String searchString = Optional.ofNullable(filter.getSearchString()).map(String::trim).orElse("" );
        if (!StringUtils.isEmpty(searchString)) {
            String pattern = "%" + searchString + "%";
            Predicate title = criteriaBuilder.like(root.get(TITLE_PATH), pattern);
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
