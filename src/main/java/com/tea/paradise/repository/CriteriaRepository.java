package com.tea.paradise.repository;

import com.tea.paradise.dto.pagination.PagingCommand;
import com.tea.paradise.dto.sorting.Sorting;
import com.tea.paradise.dto.specification.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CriteriaRepository<T,K,F> {
    Page<T> findAllWithFilters(
            PagingCommand<K,F> pagingCommand,
            Specification<T,K> specification,
            Sorting<T,F> sorting,
            Pageable pageable
    );
}
