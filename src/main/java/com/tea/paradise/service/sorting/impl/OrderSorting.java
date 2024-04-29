package com.tea.paradise.service.sorting.impl;

import com.tea.paradise.dto.pagination.Sorter;
import com.tea.paradise.enums.OrderSortType;
import com.tea.paradise.model.Orders;
import com.tea.paradise.model.PackageOrder;
import com.tea.paradise.service.sorting.Sorting;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.tea.paradise.service.specification.impl.OrderSpecification.*;

@Component
public class OrderSorting implements Sorting<Orders, OrderSortType> {
    @Override
    public List<Order> defSort(Sorter<OrderSortType> sorter, CriteriaBuilder criteriaBuilder, Root<Orders> root) {
        List<Order> orders = new ArrayList<>();
        OrderSortType sortType = sorter.getSortType();
        Join<Orders, PackageOrder> packageOrdersJoin = root.join(PACKAGE_ORDERS_PATH, JoinType.LEFT);

        switch (sortType) {
            case OLD -> orders.add(criteriaBuilder.asc(root.get(CREATED_DATE_PATH)));
            case NEW -> orders.add(criteriaBuilder.desc(root.get(CREATED_DATE_PATH)));
            case CHEAP -> {
                Expression<Double> totalPrice = criteriaBuilder.sum(criteriaBuilder.prod(
                        packageOrdersJoin.get(FIXED_PRICE_PATH),
                        packageOrdersJoin.get(QUANTITY_PATH)
                ));
                orders.add(criteriaBuilder.asc(totalPrice));
            }
            case EXPENSIVE -> {
                Expression<Double> totalPricePath = criteriaBuilder.sum(criteriaBuilder.prod(
                        packageOrdersJoin.get(FIXED_PRICE_PATH),
                        packageOrdersJoin.get(QUANTITY_PATH)
                ));
                orders.add(criteriaBuilder.desc(totalPricePath));
            }
        }
        return orders;
    }
}
