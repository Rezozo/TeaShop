package com.tea.paradise.repository;

import com.tea.paradise.dto.pagination.filters.OrderFilter;
import com.tea.paradise.enums.OrderSortType;
import com.tea.paradise.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>,
        CriteriaRepository<Orders, OrderFilter, OrderSortType> {
    List<Orders> findAllByCreatedDateBetween(ZonedDateTime start, ZonedDateTime end);
}
