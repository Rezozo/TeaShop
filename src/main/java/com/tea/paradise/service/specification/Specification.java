package com.tea.paradise.service.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public interface Specification<T,K> {
    List<Predicate> predicates(K filter, CriteriaBuilder criteriaBuilder, Root<T> root);
}
