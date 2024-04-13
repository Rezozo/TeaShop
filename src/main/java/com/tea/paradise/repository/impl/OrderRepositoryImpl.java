package com.tea.paradise.repository.impl;

import com.tea.paradise.dto.pagination.filters.OrderFilter;
import com.tea.paradise.enums.OrderSortType;
import com.tea.paradise.model.Orders;
import com.tea.paradise.repository.AbstractRepositoryImpl;

public class OrderRepositoryImpl extends AbstractRepositoryImpl<Orders, OrderFilter, OrderSortType> {
    protected OrderRepositoryImpl() {
        super(Orders.class);
    }
}
