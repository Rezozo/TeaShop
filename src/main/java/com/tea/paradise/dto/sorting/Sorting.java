package com.tea.paradise.dto.sorting;

import com.tea.paradise.dto.pagination.Sorter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

import java.util.List;

public interface Sorting<T,K> {
    List<Order> defSort(Sorter<K> sorter, CriteriaBuilder criteriaBuilder, Root<T> root);
}
