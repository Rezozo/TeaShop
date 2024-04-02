package com.tea.paradise.repository;

import com.tea.paradise.dto.pagination.Pagination;
import com.tea.paradise.dto.pagination.PagingCommand;
import com.tea.paradise.service.sorting.Sorting;
import com.tea.paradise.service.specification.Specification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

import static com.tea.paradise.service.specification.impl.ProductSpecification.ID_PATH;

public abstract class AbstractRepositoryImpl<T,K,F> implements CriteriaRepository<T,K,F> {
    @PersistenceContext
    private EntityManager entityManager;
    private final Class<T> entity;

    protected AbstractRepositoryImpl(Class<T> entity) {
        this.entity = entity;
    }

    @Override
    public Page<T> findAllWithFilters(PagingCommand<K, F> pagingWebCommand, Specification<T, K> specification, Sorting<T, F> sorting, Pageable unused) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(entity);
        Root<T> root = criteriaQuery.from(entity);

        List<Predicate> predicates = specification.predicates(pagingWebCommand.getFilter(), cb, root);
        CriteriaQuery<T> resultCriteriaQuery = criteriaQuery.select(root);

        if (Objects.nonNull(pagingWebCommand.getSorter())) {
            resultCriteriaQuery.orderBy(sorting.defSort(pagingWebCommand.getSorter(), cb, root));
            resultCriteriaQuery.groupBy(root.get(ID_PATH));
        }

        resultCriteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<T> query = entityManager.createQuery(resultCriteriaQuery);

        Pagination pagination = pagingWebCommand.getPagination();
        Pageable pageable = Pageable.unpaged();
        if (Objects.nonNull(pagination)) {
            Integer limit = pagination.getPageSize();
            Integer offset = pagination.getCurrentPage();
            query.setMaxResults(limit)
                    .setFirstResult(offset * limit);

            pageable = PageRequest.of(offset, limit);
        }
        List<T> result = query.getResultList();

        return new PageImpl<>(query.getResultList(), pageable, result.size());
    }
}
